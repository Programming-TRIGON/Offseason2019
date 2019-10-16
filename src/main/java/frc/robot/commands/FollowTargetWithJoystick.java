package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.Enums.Target;
import frc.robot.pidsources.VisionPIDSourceX;
import frc.robot.utils.Limelight.CamMode;

public class FollowTargetWithJoystick extends Command {
    private double lastTimeOnTarget;
    private Target target;
    private PIDController pidControllerX;
    private PidSettings pidSettingsX;
    private double xOutput;

    /**
     * @param target       The target to follow.
     * @param pidSettingsX PID settings for the rotation
     */
    public FollowTargetWithJoystick(Target target, PidSettings pidSettingsX) {
        requires(Robot.drivetrain);
        this.target = target;
        this.pidSettingsX = pidSettingsX;
    }

    /**
     * @param target The target to follow.
     */
    public FollowTargetWithJoystick(Target target) {
        this(target, RobotConstants.RobotPIDSettings.VISION_X_PID_SETTINGS);
    }

    @Override
    protected void initialize() {
        // setting PID X values
        PIDSource visionPIDSourceX = new VisionPIDSourceX();
        this.pidControllerX = new PIDController(pidSettingsX.getKP(), pidSettingsX.getKI(), pidSettingsX.getKD(),
                visionPIDSourceX, x -> xOutput = -x);
        pidControllerX.setSetpoint(0);
        pidControllerX.setOutputRange(-1, 1);
        pidControllerX.setAbsoluteTolerance(pidSettingsX.getTolerance());

        // setting limelight settings
        Robot.limelight.setPipeline(target);
        Robot.limelight.setCamMode(CamMode.vision);
        pidControllerX.enable();
    }

    @Override
    protected void execute() {
        // if it sees a target it will do PID on the x axis else it won't move
        if (Robot.limelight.getTv()) {
            Robot.drivetrain.curvatureDrive(xOutput,Robot.oi.driverXbox.getY(Hand.kLeft),false);
            lastTimeOnTarget = Timer.getFPGATimestamp();
        } else {
            // the target hasn't been found.
            Robot.drivetrain.arcadeDrive(0, 0);
        }
    }

    @Override
    protected boolean isFinished() {
        // if it does not detect a target for enough time it will return true
        return Timer.getFPGATimestamp() - lastTimeOnTarget > pidSettingsX.getWaitTime()
                || (pidControllerX.onTarget());
    }

    @Override
    protected void end() {
        pidControllerX.disable();
        pidControllerX.close();
        Robot.drivetrain.arcadeDrive(0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
