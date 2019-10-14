
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * this command ejects cargo until the stall stops.
 */
public class EjectCargo extends Command {
  private final static double EJECT_POWER = -0.6, TIMEOUT = 1;
  private double startTime;

  public EjectCargo() {
    requires(Robot.cargoHolder);
  }

  @Override
  protected void initialize() {
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  protected void execute() {
    Robot.cargoHolder.setHolderMotorPower(EJECT_POWER);

  }

  @Override
  protected boolean isFinished() {
    return Timer.getFPGATimestamp() - startTime > TIMEOUT;
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
