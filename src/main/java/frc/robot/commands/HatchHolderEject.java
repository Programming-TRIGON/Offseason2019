package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/** This command ejects and pulls the hatch holder */
public class HatchHolderEject extends Command {
  private boolean eject;
  // TODO: find real value
  private double waitTime = 1;

  public HatchHolderEject(boolean eject) {
    requires(Robot.hatchHolder);
    this.eject = eject;
  }

  @Override
  protected void initialize() {
    Robot.hatchHolder.setEjection(eject);
    waitTime = +Timer.getFPGATimestamp();
  }

  @Override
  protected boolean isFinished() {
    return waitTime <= Timer.getFPGATimestamp();
  }
}
