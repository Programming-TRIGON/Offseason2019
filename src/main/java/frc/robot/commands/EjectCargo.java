
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

  @Override
  protected void execute() {
    Robot.cargoHolder.setHolderMotorPower(EJECT_POWER);

  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.cargoHolder.setHolderMotorPower(0);
    Robot.cargoHolder.setIsCargoCollected(false);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
