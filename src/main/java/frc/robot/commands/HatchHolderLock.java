package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/** This command locks and unlocks the hatch holder */
public class HatchHolderLock extends Command {
  private boolean lock;
  // TODO: find real value
  private double waitTime, DEFAULT_WAIT_TIME = 1;

  public HatchHolderLock(boolean lock) {
    requires(Robot.hatchHolder);
    this.lock = lock;
    this.waitTime = DEFAULT_WAIT_TIME;
  }

  public HatchHolderLock(boolean lock, double waitTime) {
    requires(Robot.hatchHolder);
    this.lock = lock;
    this.waitTime = waitTime;
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
