package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.Enums.Target;
import frc.robot.pidsources.VisionPIDSourceX;
import frc.robot.pidsources.VisionPIDSourceY;
import frc.robot.utils.Limelight.CamMode;
import frc.robot.utils.Limelight.LedMode;

public class FollowTarget extends Command {
    private double lastTimeOnTarget;
    private Target target;
    private PIDController pidControllerY, pidControllerX;
    private PidSettings pidSettingsY, pidSettingsX;
    private double xOutput, yOutput;
    private boolean closeToTarget;

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
    }

    /**
     * @param target The target to follow.
     */
    public FollowTarget(Target target) {
        this(target, RobotConstants.RobotPIDSettings.VISION_Y_PID_SETTINGS, RobotConstants.RobotPIDSettings.VISION_X_PID_SETTINGS);
    }

    @Override
    protected void initialize() {
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
        pidControllerY.setOutputRange(-1, 1);
        pidControllerY.setAbsoluteTolerance(pidSettingsY.getTolerance());

        // setting limelight settings
        Robot.limelight.setPipeline(target);
        Robot.limelight.setCamMode(CamMode.vision);
        Robot.limelight.setLedMode(LedMode.on);
        
        closeToTarget = Robot.limelight.getDistance() < 15;
        
        pidControllerX.enable();
        pidControllerY.enable();
    }

    @Override
    protected void execute() {
        // if it sees a target it will do PID on the x axis else it won't move
        if (Robot.limelight.getTv()) {
            if(Math.abs(yOutput) >= 0.5) {
                Robot.drivetrain.curvatureDrive(xOutput,-0.5,false);
            } else {
                Robot.drivetrain.curvatureDrive(xOutput*(closeToTarget ? 3 : 1),yOutput*(closeToTarget ? 3.5 : 1.5),false);
            }
            lastTimeOnTarget = Timer.getFPGATimestamp();
        } else {
            // the target hasn't been found.
            Robot.drivetrain.arcadeDrive(0, 0);
        }
    }

    @Override
    protected boolean isFinished() {
        return (Timer.getFPGATimestamp() - lastTimeOnTarget > pidSettingsX.getWaitTime())
                || (Robot.limelight.getDistance() < 0.8 && pidControllerY.onTarget()) 
                || (pidControllerX.onTarget() && pidControllerY.onTarget());
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
}
