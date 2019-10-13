
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * this command collects cargo until it detects a stall.
 */
public class CollectCargo extends Command {
    // TODO: calibrate power.
    private final static double POWER = 0.3;

    public CollectCargo() {
        requires(Robot.cargoHolder);
    }

    @Override
    protected void execute() {
        Robot.cargoHolder.setHolderMotorPower(POWER);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
      Robot.cargoHolder.setIsCargoCollected(Robot.cargoHolder.isCargoCollectedStall());
      Robot.cargoHolder.setHolderMotorPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
