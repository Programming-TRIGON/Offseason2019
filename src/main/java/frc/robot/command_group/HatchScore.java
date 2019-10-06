package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.HatchHolderEject;
import frc.robot.commands.HatchHolderLock;

/**
 * this command group scores the hatch: unlocks it, ejects the hatch, pulls, and
 * then locks it
 */
public class HatchScore extends CommandGroup {

  public HatchScore() {
    addSequential(new HatchHolderEject(true));
    addSequential(new HatchHolderLock(false));
    addSequential(new HatchHolderEject(false));
    addSequential(new HatchHolderLock(true));
  }
}
