package frc.robot.commandGroup;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.HatchHolderEject;
import frc.robot.commands.HatchHolderLock;
/** this command group scores the hatch: unlocks it, ejects the hatch, pulls, and then locks it */
public class HatchScore extends CommandGroup {

  public HatchScore() {
    //TODO: set wait time
    addSequential(new HatchHolderLock(false));
    addSequential(new WaitCommand(1));
    addSequential(new HatchHolderEject(true));
    addSequential(new WaitCommand(1));
    addSequential(new HatchHolderEject(false));
    addSequential(new WaitCommand(1));
    addSequential(new HatchHolderLock(true));
  }
}
