
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * this command ejects cargo until the stall stops.
 */
public class EjectCargo extends Command {
  //TODO: calibrate power.
  private final static double EJECT_POWER = -0.3,TIMEOUT = 2;
  public EjectCargo() {
    requires(Robot.cargoHolder);
    setTimeout(TIMEOUT);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.cargoHolder.setHolderMotorPower(EJECT_POWER);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.cargoHolder.setHolderMotorPower(0);
    Robot.cargoHolder.setIsCargoCollected(false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.cargoHolder.setHolderMotorPower(0);
    Robot.cargoHolder.setIsCargoCollected(false);
  }
}
