package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Enums.Target;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.pidsources.VisionPIDSourceX;
import frc.robot.pidsources.VisionPIDSourceY;
import frc.robot.utils.Limelight.CamMode;

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

    /**
     * In this constructor the robot turns in place with PidSettings you gave it.
     */
    public VisionPID(Target target, PidSettings pidSettingsX) {
        requires(Robot.drivetrain);
        this.target = target;

        this.pidSettingsX = pidSettingsX;
        // setting PID X values
        PIDSource visionPIDSourceX = new VisionPIDSourceX();

        this.pidControllerX = new PIDController(pidSettingsX.getKP(), pidSettingsX.getKI(), pidSettingsX.getKD(),
                visionPIDSourceX, output -> xOutput = -output);
        pidControllerX.setOutputRange(-1, 1);
        pidControllerX.setAbsoluteTolerance(pidSettingsX.getTolerance());
        pidControllerX.setSetpoint(target.getSetpointX());

    }

    /**
     * In this constructor the robot turns in place or while driving twards the
     * target with set PidSettings.
     */
    public VisionPID(Target target, boolean isDrivingForward) {
        this(target, RobotConstants.RobotPIDSettings.VISION_X_PID_SETTINGS);
        if (isDrivingForward)
            createPIDControllerY(target, RobotConstants.RobotPIDSettings.VISION_Y_PID_SETTINGS);
    }

    /**
     * In this constructor the robot turns while moving towards the target with
     * PidSettings you gave it.
     */
    public VisionPID(Target target, PidSettings pidSettingsX, PidSettings pidSettingsY) {
        this(target, pidSettingsX);
        createPIDControllerY(target, pidSettingsY);
    }

    private void createPIDControllerY(Target target, PidSettings pidSettingsY) {
        this.pidSettingsY = pidSettingsY;
        PIDSource visionPIDSourceY = new VisionPIDSourceY();
        this.pidControllerY = new PIDController(pidControllerY.getP(), pidSettingsY.getKI(), pidSettingsY.getKD(),
                visionPIDSourceY, output -> yOutput = -output);
        pidControllerY.setOutputRange(-1, 1);
        pidControllerY.setAbsoluteTolerance(pidSettingsY.getTolerance());
        pidControllerY.setSetpoint(target.getSetpointY());
    }

    /**
     * The robot will drive straight with the correction of the vision.
     */
    public VisionPID(Target target, Supplier<Double> forwardSupplier, Button button) {
        this(target, false);
        this.forwardSupplier = forwardSupplier;
        this.button = button;
    }

    @Override
    protected void initialize() {

        // setting Vision values
        Robot.limelight.setPipeline(target);
        Robot.limelight.setCamMode(CamMode.vision);
        pidControllerX.reset();
        pidControllerX.enable();
        if (pidControllerY != null) {
            pidControllerY.reset();
            pidControllerY.enable();
        }

    }

    @Override
    protected void execute() {
        // powering the motors
        if (pidControllerY != null)
            Robot.drivetrain.curvatureDrive(xOutput, yOutput, false);
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
        if (pidControllerY != null)
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
    }

    @Override
    protected void interrupted() {
        end();
    }

    /**
     * @param pidSettings pid settings to be set by testPID for x movement
     */
    public void setPID(PidSettings pidSettings) {
        if (!isRunning())
            pidControllerX.setPID(pidSettings.getKP(), pidSettings.getKI(), pidSettings.getKD());
    }

    /**
     * @param pidSettings2 pid settings to be set by testPID for y movement
     */
    public void setPID2(PidSettings pidSettings2) {
        if (!isRunning() && this.pidSettingsY != null)
            pidControllerY.setPID(pidSettings2.getKP(), pidSettings2.getKI(), pidSettings2.getKD());
    }
}


