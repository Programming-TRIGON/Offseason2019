package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/** This command ejects and pulls the hatch holder */
public class HatchHolderEject extends Command {
  private boolean eject;
  // TODO: find real value
  private double waitTime;

  public HatchHolderEject(boolean eject, double waitTime) {
    requires(Robot.hatchHolder);
    this.eject = eject;
    this.waitTime = waitTime;
  }

  public HatchHolderEject(boolean eject) {
    this(eject, 1);
  }

  @Override
  protected void initialize() {
    Robot.hatchHolder.setEjection(eject);
    waitTime += Timer.getFPGATimestamp();
  }

  @Override
  protected boolean isFinished() {
    return waitTime <= Timer.getFPGATimestamp();
  }
}
