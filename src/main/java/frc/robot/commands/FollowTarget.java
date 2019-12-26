package frc.robot.commands;

import com.spikes2212.dashboard.ConstantHandler;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Enums.Target;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.RobotConstants.RobotPIDSettings;
import frc.robot.pidsources.VisionPIDSourceX;
import frc.robot.pidsources.VisionPIDSourceY;
import frc.robot.utils.Limelight.CamMode;
import frc.robot.utils.Limelight.LedMode;
import frc.robot.utils.VisionLocator;

import java.util.function.Supplier;

public class FollowTarget extends Command {
    //TODO: change MIN_X_DISPLACEMENT_TO_CHASE and DEFAULT_Y_POWER.
    private static final double MIN_X_DISPLACEMENT_TO_CHASE = 5.0;
    private static final double DEFAULT_Y_POWER = -0.2;
    private double lastTimeOnTarget;
    private double ledsWaitTime;
    private Target target;
    private PIDController pidControllerY, pidControllerX;
    private PidSettings pidSettingsY, pidSettingsX;
    private VisionLocator visionLocator;
    private double xOutput, yOutput;
    private boolean closeToTarget;
    private boolean isChasing;
    private double xDisplacamentP;
    private Supplier<Double> pSupplier = ConstantHandler.addConstantDouble("vision x kp", 0);

    /**
     * @param target       The target to follow.
     * @param pidSettingsY PID settings for the distance
     * @param pidSettingsX PID settings for the rotation
     */
    public FollowTarget(Target target, PidSettings pidSettingsY, PidSettings pidSettingsX) {
        requires(Robot.drivetrain);
        this.target = target;
        this.pidSettingsY = pidSettingsY;
        this.pidSettingsX = pidSettingsX;
        visionLocator = new VisionLocator(target);
    }

    /**
     * @param target The target to follow.
     */
    public FollowTarget(Target target) {
        this(target, RobotConstants.RobotPIDSettings.VISION_Y_PID_SETTINGS, RobotConstants.RobotPIDSettings.VISION_X_PID_SETTINGS);
    }

    @Override
    protected void initialize() {
        xDisplacamentP = pSupplier.get();
        // setting PID X values
        PIDSource visionPIDSourceX = new VisionPIDSourceX();
        PIDSource visionPIDSourceY = new VisionPIDSourceY();
        this.pidControllerX = new PIDController(pidSettingsX.getKP(), pidSettingsX.getKI(), pidSettingsX.getKD(),
                visionPIDSourceX, x -> xOutput = -x);
        pidControllerX.setSetpoint(0);
        pidControllerX.setOutputRange(-1, 1);
        pidControllerX.setAbsoluteTolerance(pidSettingsX.getTolerance());

        // setting PID Y values
        this.pidControllerY = new PIDController(pidSettingsY.getKP(), pidSettingsY.getKI(), pidSettingsY.getKD(),
                visionPIDSourceY, y -> yOutput = -y);
        pidControllerY.setSetpoint(0);
        pidControllerY.setOutputRange(-0.5, 0.5);
        pidControllerY.setAbsoluteTolerance(pidSettingsY.getTolerance());

        // setting limelight settings
        Robot.limelight.setPipeline(target);
        Robot.limelight.setCamMode(CamMode.vision);
        Robot.limelight.setLedMode(LedMode.on);

        // closeToTarget = Robot.limelight.getDistance() < 15;
        // System.out.println(closeToTarget);

        pidControllerX.enable();
        pidControllerY.enable();
        ledsWaitTime = 0;
        isChasing = false;
        NetworkTableInstance.getDefault().flush();
    }

    @Override
    protected void execute() {
        if (isChasing /*|| !DriverStation.getInstance().isAutonomous()*/)
            executeChase();
        else
            executeMinimizeX();
    }

    private void executeMinimizeX() {
        if (Robot.limelight.getTv() && timeSinceInitialized() > ledsWaitTime) {
            double xDisplacement = visionLocator.calculateXDisplacement();
            double rotationPower = xDisplacamentP * xDisplacement;
            lastTimeOnTarget = Timer.getFPGATimestamp();
            if (Math.abs(xDisplacement) < MIN_X_DISPLACEMENT_TO_CHASE) {
                isChasing = true;
            }
            else {
                System.out.println("x: " +rotationPower);
                Robot.drivetrain.arcadeDrive(rotationPower, DEFAULT_Y_POWER);
            }
        }
        else
            // the target hasn't been found.
            Robot.drivetrain.arcadeDrive(0,0);
    }

    private void executeChase() {
        // if it sees a target it will do PID on the x axis else it won't move
        if (Robot.limelight.getTv() && timeSinceInitialized() > ledsWaitTime) {
            Robot.drivetrain.arcadeDrive(xOutput, yOutput - 0.025);
            lastTimeOnTarget = Timer.getFPGATimestamp();
        } else {
            // the target hasn't been found.
            Robot.drivetrain.arcadeDrive(0, 0);
        }
    }

    @Override
    protected boolean isFinished() {
        return (Timer.getFPGATimestamp() - lastTimeOnTarget > pidSettingsX.getWaitTime())
                || Robot.limelight.getDistance() < pidSettingsY.getTolerance();
    }

    @Override
    protected void end() {
        System.out.println("finished");
        pidControllerX.disable();
        pidControllerY.disable();
        pidControllerX.close();
        pidControllerY.close();
        Robot.drivetrain.arcadeDrive(0, 0);
        Robot.limelight.setCamMode(CamMode.driver);
        Robot.limelight.setLedMode(LedMode.off);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
