package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AfterHatchPlacement extends Command {
  boolean isLock;

  public AfterHatchPlacement() {
    requires(Robot.hatchHolder);
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
    //Robot.drivetrain.getCurrentCommand().cancel();
    isLock = Robot.hatchHolder.isLock();
    DriveArcade.toggleLockX();
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
    DriveArcade.toggleLockX();
    Robot.hatchHolder.setLock(true);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
