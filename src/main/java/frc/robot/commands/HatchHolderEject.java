package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/** This command ejects and unejects the hatch holder */
public class HatchHolderEject extends InstantCommand {
  private boolean eject;

  public HatchHolderEject(boolean eject) {
    super();
    requires(Robot.hatchHolder);
    this.eject = eject;
  }

  @Override
  protected void initialize() {
    Robot.hatchHolder.setEjection(eject);
  }
}
