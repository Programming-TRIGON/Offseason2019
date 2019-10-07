
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * this command keeps the cargo inside the holder. runs by defualt.
 */
public class keepCargo extends Command {
  // TODO: calibrate power.
  private final static double STALL_POWER = 0.05;
  private final static double FULL_POWER = 0.4;

  public keepCargo() {
    requires(Robot.cargoHolder);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.cargoHolder.isCargoCollected()) {
      // The cargo is supposed to be collected. Checks if the cargo needs to be
      // recollected.
      if (Robot.cargoHolder.isCargoCollectedStall()) {
        // keeps the cargo inside.
        Robot.cargoHolder.setHolderMotorPower(STALL_POWER);
      } else
        // recollects the cargo
        Robot.cargoHolder.setHolderMotorPower(FULL_POWER);
    } else
      Robot.cargoHolder.setHolderMotorPower(0);
  }

  // The command always runs until it is interuppted.
  @Override
  protected boolean isFinished() {
    return false;
  }

  // this shouldn't ever be called
  @Override
  protected void end() {
    // this shouldn't be called
    Robot.cargoHolder.setHolderMotorPower(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.cargoHolder.setHolderMotorPower(0);
  }
}
