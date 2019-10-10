package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.HatchHolderEject;
import frc.robot.commands.HatchHolderLock;

public class EjectHatch extends CommandGroup {
/**
 * this command group Ejects the hatch: unlocks it, ejects the hatch, pulls, and
 * then locks it
 */
  public EjectHatch() {
    addSequential(new HatchHolderEject(true));
    addSequential(new HatchHolderLock(false));
    addSequential(new HatchHolderEject(false));
    addSequential(new HatchHolderLock(true));
  }
}
