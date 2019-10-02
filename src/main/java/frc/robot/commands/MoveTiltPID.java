

package frc.robot.commands;

import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotConstants;

public class MoveTiltPID extends Command {
  private double setpoint;
  private PIDSettings settings;
  private PIDController pidController;

  public MoveTiltPID(double setpoint, PIDSettings settings) {
    requires(Robot.tilt);
    this.settings = settings;

    this.setpoint = setpoint;
  }

  public MoveTiltPID(double setpoint) {
    this(setpoint, RobotConstants.RobotPIDSettings.TILT_PID_SETTINGS);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.pidController = new PIDController(settings.getKP(), settings.getKI(), settings.getKD(),
        Robot.tilt.getPIDSource(), Robot.tilt::moveTilt);
    pidController.setSetpoint(setpoint);
    pidController.setOutputRange(-1, 1);
    pidController.enable();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    pidController.disable();
    pidController.close();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
