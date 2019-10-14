package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/** This command ejects and pulls the hatch holder */
public class HatchHolderEject extends Command {
  private boolean eject;
  // TODO: find real value
  private static double DEFAULT_WAIT_TIME = 0.75;
  private double waitTime;

  public HatchHolderEject(boolean eject) {
    this(eject, DEFAULT_WAIT_TIME);
  }
  
  public HatchHolderEject(boolean eject, double waitTime) {
    requires(Robot.hatchHolder);
    this.eject = eject;
    this.waitTime = waitTime;
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
