package frc.robot.commands;

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

public class FollowTarget extends Command {
  //TODO: change MIN_X_DISPLACEMENT_TO_CHASE and DEFAULT_Y_POWER.
  private static final double MIN_X_DISPLACEMENT_TO_CHASE = 5.0;
  private static final double DEFAULT_Y_POWER = 0.2;
  private double lastTimeOnTarget;
  private Target target;
  private PIDController pidControllerY, pidControllerX;
  private PidSettings pidSettingsY, pidSettingsX;
  private VisionLocator visionLocator;
  private double xOutput, yOutput;
  private boolean closeToTarget;
  private boolean isChasing;

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
    isChasing = false;
  }

  @Override
  protected void execute() {
    if (isChasing || !DriverStation.getInstance().isAutonomous())
      executeChase();
    else
      executeMinimizeX();
  }

  private void executeMinimizeX() {
    if (!Robot.limelight.getTv()) {
      double xDisplacement = visionLocator.calculateXDisplacement();
      double rotationPower = RobotPIDSettings.KP_X_DISPLACEMENT * xDisplacement;
      Robot.drivetrain.arcadeDrive(rotationPower, DEFAULT_Y_POWER);
      if (Math.abs(xDisplacement) < MIN_X_DISPLACEMENT_TO_CHASE) {
        isChasing = true;
      }
    }
    else
      // the target hasn't been found.
      Robot.drivetrain.arcadeDrive(0,0);
  }

  private void executeChase() {
    // if it sees a target it will do PID on the x axis else it won't move
    if (Robot.limelight.getTv()) {
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
