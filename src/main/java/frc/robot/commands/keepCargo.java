
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class keepCargo extends Command {
  // TODO: calibrate power.
  private final static double STALL_POWER = 0.05;
  private final static double FULL_POWER = 0.4;

  public keepCargo() {
    requires(Robot.cargoHolder);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.cargoHolder.isCargoCollected()){
      if(Robot.cargoHolder.isCargoCollectedStall()){
        Robot.cargoHolder.setHolderMotorPower(STALL_POWER);
      }
      else
        Robot.cargoHolder.setHolderMotorPower(FULL_POWER);
    }
    else
        Robot.cargoHolder.setHolderMotorPower(0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.cargoHolder.setHolderMotorPower(0);
  }
}
