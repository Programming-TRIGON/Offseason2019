
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveTiltLinear extends Command {
  // TODO: change value.
  private static final double MOVE_POWER = 0;
  private double setpoint;
  private double EPSILON = 0.1;

  public MoveTiltLinear(double setpoint) {
    requires(Robot.tilt);
    this.setpoint = setpoint;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (setpoint > Robot.tilt.getAngle())
      Robot.tilt.moveTilt(MOVE_POWER);
    else
      Robot.tilt.moveTilt(-MOVE_POWER);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    double angle = Robot.tilt.getAngle();
    return Math.abs(angle - setpoint) < EPSILON;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.tilt.moveTilt(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
