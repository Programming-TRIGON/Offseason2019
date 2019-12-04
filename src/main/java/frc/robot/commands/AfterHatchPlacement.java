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
    Robot.drivetrain.getCurrentCommand().cancel();
    isLock = Robot.hatchHolder.isLock();
  }

  @Override
  protected void execute() {
    DriveArcade.toggleLockX();
  }

  @Override
  protected boolean isFinished() {
    return Robot.limelight.getDistance() >= 15 || !Robot.limelight.getTv() || isLock;
  }

  @Override
  protected void end() {
    DriveArcade.toggleLockX();
    Robot.hatchHolder.setLock(true);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
