package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotComponents;
import frc.robot.RobotConstants;
import frc.robot.Enums.Target;
import frc.robot.pidsources.VisionPIDSourceX;
import frc.robot.pidsources.VisionPIDSourceY;

/** A command for turning in place and driving straight with vision correction. */
public class VisionPID extends Command {
  PIDController pidControllerX;
  PidSettings pidSettingsX, pidSettingsY;
  Button button;
  Target target;
  double timeOnTarget;
  Supplier<Double> forwardSupplier = () -> 0.0;
  boolean isPIDOnY;
  PIDSource visionPIDSourceX, visionPIDSourceY;
  PIDOutput xOutput, yOutput;
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
   * In this constructor the robot turns while moving towards the target with PidSettings you gave it.
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
    // setting PID X values
    visionPIDSourceX = new VisionPIDSourceX();
    visionPIDSourceY = new VisionPIDSourceY();
    this.pidControllerX = new PIDController(pidSettingsX.getKP(), pidSettingsX.getKI(), pidSettingsX.getKD(),
        visionPIDSourceX, xOutput);
    pidControllerX.setContinuous(true);
    pidControllerX.setOutputRange(-1, 1);
    pidControllerX.setAbsoluteTolerance(pidSettingsX.getTolerance());
    pidControllerX.setSetpoint(target.getSetpointX());
    pidControllerX.enable();
  }

  @Override
  protected void execute() {
    if (!pidControllerX.onTarget())
      timeOnTarget = Timer.getFPGATimestamp();
  }

  @Override
  protected boolean isFinished() {
    boolean onTarget = (Timer.getFPGATimestamp() - timeOnTarget) > pidSettingsX.getWaitTime();
    return button != null ? !button.get() : onTarget;
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
