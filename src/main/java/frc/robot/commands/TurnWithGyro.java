package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotComponents;
import frc.robot.RobotConstants;

/** A command for turning in place and driving straight with gyro correction. */
public class TurnWithGyro extends Command {
  PIDController pidController;
  PidSettings pidSettings;
  Button button;
  double angle, timeOnTarget;
  Supplier<Double> forwardSupplier = () -> 0.0;

  /**
   * In this constructor the robot turns in place with PidSettings you gave it.
   */
  public TurnWithGyro(double angle, PidSettings pidSettings) {
    requires(Robot.drivetrain);
    this.angle = angle;
    this.pidSettings = pidSettings;
  }

  /** In this constructor the robot turns in place with set PidSettings. */
  public TurnWithGyro(double angle) {
    this(angle, RobotConstants.RobotPIDSettings.DRIVETRAIN_TURN_PID_SETTINGS);
  }

  /** The robot will drive straight with the correction of the gyro. */
  public TurnWithGyro(double angle, Supplier<Double> forwardSupplier, Button button) {
    requires(Robot.drivetrain);
    this.angle = angle;
    this.forwardSupplier = forwardSupplier;
    this.pidSettings = RobotConstants.RobotPIDSettings.DRIVETRAIN_TURN_PID_SETTINGS;
    this.button = button;
  }

  @Override
  protected void initialize() {
    this.pidController = new PIDController(pidSettings.getKP(), pidSettings.getKI(), pidSettings.getKD(),
        RobotComponents.Drivetrain.GYRO, output -> Robot.drivetrain.arcadeDrive(output, forwardSupplier.get()));
    pidController.setContinuous(true);
    pidController.setOutputRange(-1, 1);
    pidController.setAbsoluteTolerance(pidSettings.getTolerance());
    pidController.setSetpoint(angle);
    pidController.enable();
  }

  @Override
  protected void execute() {
    if (!pidController.onTarget())
      timeOnTarget = Timer.getFPGATimestamp();
  }

  @Override
  protected boolean isFinished() {
    return (Timer.getFPGATimestamp() - timeOnTarget) > pidSettings.getWaitTime(); 
  }

  @Override
  protected void end() {
    pidController.disable();
    pidController.close();
    Robot.drivetrain.arcadeDrive(0, 0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
