package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.Enums.Target;
import frc.robot.pidsources.VisionPIDSourceX;
import frc.robot.pidsources.VisionPIDSourceY;
import frc.robot.utils.Limelight.CamMode;

/**
 * A command for turning in place and driving straight with vision correction.
 */
public class VisionPID extends Command {
  PIDController pidControllerX, pidControllerY;
  PidSettings pidSettingsX, pidSettingsY;
  Button button;
  Target target;
  double timeOnTargetX, timeOnTargetY, yOutput, xOutput;
  Supplier<Double> forwardSupplier = () -> 0.0;
  boolean isPIDOnY, onTarget;
  PIDSource visionPIDSourceX, visionPIDSourceY;

  /**
   * In this constructor the robot turns in place with PidSettings you gave it.
   */
  public VisionPID(Target target, PidSettings pidSettingsX) {
    requires(Robot.drivetrain);
    this.target = target;
    this.isPIDOnY = false;
    this.pidSettingsX = pidSettingsX;
    this.pidSettingsY = RobotConstants.RobotPIDSettings.VISION_X_PID_SETTINGS;
  }

  /** In this constructor the robot turns in place with set PidSettings. */
  public VisionPID(Target target, boolean isPIDOnY) {
    this(target, RobotConstants.RobotPIDSettings.VISION_X_PID_SETTINGS);
    this.isPIDOnY = isPIDOnY;
  }

  /**
   * In this constructor the robot turns while moving towards the target with
   * PidSettings you gave it.
   */
  public VisionPID(Target target, PidSettings pidSettingsX, PidSettings pidSettingsY) {
    requires(Robot.drivetrain);
    this.target = target;
    this.isPIDOnY = true;
    this.pidSettingsX = pidSettingsX;
    this.pidSettingsY = pidSettingsY;
  }

  /** The robot will drive straight with the correction of the vision. */
  public VisionPID(Target target, Supplier<Double> forwardSupplier, Button button) {
    requires(Robot.drivetrain);
    this.target = target;
    this.forwardSupplier = forwardSupplier;
    this.pidSettingsX = RobotConstants.RobotPIDSettings.VISION_X_PID_SETTINGS;
    this.button = button;
    this.isPIDOnY = false;
  }

  @Override
  protected void initialize() {

    // setting Vision values
    Robot.limelight.setPipeline(target);
    Robot.limelight.setCamMode(CamMode.vision);

    // setting PID X values
    visionPIDSourceX = new VisionPIDSourceX();
    visionPIDSourceY = new VisionPIDSourceY();
    this.pidControllerX = new PIDController(pidSettingsX.getKP(), pidSettingsX.getKI(), pidSettingsX.getKD(),
        visionPIDSourceX, output -> xOutput = -output);
    pidControllerX.setOutputRange(-1, 1);
    pidControllerX.setAbsoluteTolerance(pidSettingsX.getTolerance());
    pidControllerX.setSetpoint(target.getSetpointX());

    // setting PID Y values
    if (isPIDOnY) {
      this.pidControllerY = new PIDController(pidControllerY.getP(), pidSettingsY.getKI(), pidSettingsY.getKD(),
          visionPIDSourceY, output -> yOutput = -output);
      pidControllerY.setOutputRange(-1, 1);
      pidControllerY.setAbsoluteTolerance(pidSettingsY.getTolerance());
      pidControllerY.setSetpoint(target.getSetpointY());
      pidControllerY.enable();
    }
    pidControllerX.enable();
  }

  @Override
  protected void execute() {
    // powering the motors
    if (isPIDOnY)
      Robot.drivetrain.curvatureDrive(xOutput, yOutput, false);
    else
      Robot.drivetrain.curvatureDrive(yOutput, forwardSupplier.get(), false);

    // this is to find when the robot is done or does not see the target
    if (!pidControllerX.onTarget() || Robot.limelight.getTv())
      timeOnTargetX = Timer.getFPGATimestamp();

    if (!pidControllerY.onTarget() || Robot.limelight.getTv())
      timeOnTargetY = Timer.getFPGATimestamp();
  }

  @Override
  protected boolean isFinished() {
    if (isPIDOnY)
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
}
