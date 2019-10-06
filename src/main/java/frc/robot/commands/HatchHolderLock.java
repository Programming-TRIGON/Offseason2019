package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/** This command locks and unlocks the hatch holder */
public class HatchHolderLock extends InstantCommand {
  private boolean lock;

  public HatchHolderLock(boolean lock) {
    super();
    requires(Robot.hatchHolder);
    this.lock = lock;
  }

  @Override
  protected void initialize() {
    Robot.hatchHolder.setLock(lock);
  }
}
