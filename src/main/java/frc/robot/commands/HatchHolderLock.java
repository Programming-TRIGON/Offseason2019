package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

// ISSUE! - The commands leaks and not ending, for now finishs instantly 

/** This command locks and unlocks the hatch holder */
public class HatchHolderLock extends Command {
  private boolean lock;
  private static final double DEFAULT_WAIT_TIME = 0.75;
  private double waitTime;

  public HatchHolderLock(boolean lock, double waitTime) {
    requires(Robot.hatchHolder);
    this.lock = lock;
    //this.waitTime = waitTime;
  }
  
  public HatchHolderLock(boolean lock) {
    this(lock, DEFAULT_WAIT_TIME);
  }

  @Override
  protected void initialize() {
    Robot.hatchHolder.setLock(lock);
    // waitTime += Timer.getFPGATimestamp();
  }

  @Override
  protected boolean isFinished() {
    // return waitTime <= Timer.getFPGATimestamp();
    return true;
  }
}
