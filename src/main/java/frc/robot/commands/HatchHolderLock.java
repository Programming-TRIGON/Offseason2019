package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/** This command locks and unlocks the hatch holder */
public class HatchHolderLock extends Command {
  private boolean lock;
  // TODO: find real value
  private double waitTime;

  public HatchHolderLock(boolean lock, double waitTime) {
    requires(Robot.hatchHolder);
    this.lock = lock;
  }

  public HatchHolderLock(boolean lock) {
    this(lock, 1);
  }

  @Override
  protected void initialize() {
    Robot.hatchHolder.setLock(lock);
    waitTime += Timer.getFPGATimestamp();
  }

  @Override
  protected boolean isFinished() {
    return waitTime <= Timer.getFPGATimestamp();
  }
}
