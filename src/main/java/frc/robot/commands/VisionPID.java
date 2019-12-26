package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.enums.Target;
import frc.robot.pidsources.VisionPIDSourceX;
import frc.robot.pidsources.VisionPIDSourceY;
import frc.robot.utils.Limelight.CamMode;
import frc.robot.utils.Limelight.LedMode;

import java.util.function.Supplier;

/**
 * A command for turning in place and driving straight with vision correction.
 */
public class VisionPID extends Command {
    private PIDController pidControllerX, pidControllerY;
    private PidSettings pidSettingsX, pidSettingsY;
    private Button button;
    private Target target;
    private double timeOnTargetX, timeOnTargetY, yOutput, xOutput;
    private Supplier<Double> forwardSupplier = () -> 0.0;
    private boolean isDrivingForward = false;

    /**
     * In this constructor the robot turns in place with PidSettings you gave it.
     */
    public VisionPID(Target target, PidSettings pidSettingsX) {
        requires(Robot.drivetrain);
        this.target = target;
        this.pidSettingsX = pidSettingsX;
        this.pidSettingsY = RobotConstants.RobotPIDSettings.VISION_Y_PID_SETTINGS;
        this.button = null;
    }

    /**
     * In this constructor the robot turns in place or while driving twards the
     * target with set PidSettings.
     */
    public VisionPID(Target target, boolean isDrivingForward) {
        this(target, RobotConstants.RobotPIDSettings.VISION_X_PID_SETTINGS);
        this.isDrivingForward = isDrivingForward;
    }

    /**
     * In this constructor the robot turns while moving towards the target with
     * PidSettings you gave it.
     */
    public VisionPID(Target target, PidSettings pidSettingsX, PidSettings pidSettingsY) {
        requires(Robot.drivetrain);
        this.target = target;
        this.isDrivingForward = true;
        this.pidSettingsX = pidSettingsX;
        this.pidSettingsY = pidSettingsY;
        this.button = null; 
    }

    /**
     * The robot will drive straight with the correction of the vision.
     */
    public VisionPID(Target target, Supplier<Double> forwardSupplier, Button button) {
        requires(Robot.drivetrain);
        this.target = target;
        this.forwardSupplier = forwardSupplier;
        this.pidSettingsX = RobotConstants.RobotPIDSettings.VISION_X_PID_SETTINGS;
        this.button = button;
    }

    @Override
    protected void initialize() {

        // setting Vision values
        Robot.limelight.setPipeline(target);
        Robot.limelight.setCamMode(CamMode.vision);
        Robot.limelight.setLedMode(LedMode.on);

        // setting PID X values
        PIDSource visionPIDSourceX = new VisionPIDSourceX();
        PIDSource visionPIDSourceY = new VisionPIDSourceY();
        this.pidControllerX = new PIDController(pidSettingsX.getKP(), pidSettingsX.getKI(), pidSettingsX.getKD(),
                visionPIDSourceX, output -> xOutput = -output);
        pidControllerX.setOutputRange(-1, 1);
        pidControllerX.setAbsoluteTolerance(pidSettingsX.getTolerance());
        pidControllerX.setSetpoint(RobotConstants.Vision.ANGLE_FROM_TARGET);

        // setting PID Y values
        if (isDrivingForward) {
            this.pidControllerY = new PIDController(pidSettingsY.getKP(), pidSettingsY.getKI(), pidSettingsY.getKD(),
                    visionPIDSourceY, output -> yOutput = output);
            pidControllerY.setOutputRange(-1, 1);
            pidControllerY.setAbsoluteTolerance(pidSettingsY.getTolerance());
            pidControllerY.setSetpoint(RobotConstants.Vision.DISTANCE_FROM_TARGET);
            pidControllerY.enable();
        }
        pidControllerX.enable();
    }

    @Override
    protected void execute() {
        // powering the motors
        if (isDrivingForward)
            Robot.drivetrain.arcadeDrive(xOutput, yOutput);
        else
            Robot.drivetrain.curvatureDrive(xOutput, forwardSupplier.get(), false);

        // this is to find when the robot is done or does not see the target
        if (!pidControllerX.onTarget() && Robot.limelight.getTv())
            timeOnTargetX = Timer.getFPGATimestamp();

        if (!pidControllerY.onTarget() && Robot.limelight.getTv())
            timeOnTargetY = Timer.getFPGATimestamp();
    }

    @Override
    protected boolean isFinished() {
        /*
         * if the time that has passes since he was on target or that he hasn't seen the
         * vision target he will finish
         */
        boolean onTarget;
        if (isDrivingForward)
            onTarget = (Timer.getFPGATimestamp() - timeOnTargetX) > pidSettingsX.getWaitTime()
                    && (Timer.getFPGATimestamp() - timeOnTargetY) > pidSettingsY.getWaitTime();
        else
            onTarget = (Timer.getFPGATimestamp() - timeOnTargetX) > pidSettingsX.getWaitTime();
        return button != null ? !button.get() : onTarget;
    }

    @Override
    protected void end() {
        pidControllerX.disable();
        pidControllerY.disable();
        pidControllerX.close();
        pidControllerY.close();
        Robot.drivetrain.arcadeDrive(0, 0);
        //Robot.limelight.setLedMode(LedMode.off);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
