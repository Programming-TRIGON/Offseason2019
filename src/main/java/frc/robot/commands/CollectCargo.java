
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * this command collects cargo until it detects a stall.
 */
public class CollectCargo extends Command {
    private final static double POWER = 0.5;
    private static final double CollectTime = 1;
    private double startTime;

    public CollectCargo() {
        requires(Robot.cargoHolder);
    }

    @Override
    protected void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
        Robot.cargoHolder.setHolderMotorPower(POWER);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.cargoHolder.isCargoCollectedStall() &&
                Timer.getFPGATimestamp() - startTime > CollectTime;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.cargoHolder.setIsCargoCollected(true);
        Robot.cargoHolder.setHolderMotorPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.cargoHolder.setIsCargoCollected(false);
        Robot.cargoHolder.setHolderMotorPower(0);
    }
}
