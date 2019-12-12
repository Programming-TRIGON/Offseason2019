package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AfterHatchPlacement extends Command {
  boolean isLock;

  public AfterHatchPlacement() {
    requires(Robot.hatchHolder);
  }

  @Override
  protected void initialize() {
    isLock = Robot.hatchHolder.isLock();
    Robot.drivetrain.setXLock(true);
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return Robot.limelight.getDistance() >= 15 || !Robot.limelight.getTv() || isLock;
  }

  @Override
  protected void end() {
    Robot.drivetrain.setXLock(false);
    Robot.hatchHolder.setLock(true);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
