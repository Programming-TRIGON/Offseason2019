package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.HatchHolderEject;
import frc.robot.commands.HatchHolderLock;

public class CollectHatch extends CommandGroup {
  /**
   * Collects a hatch, used to collect hatch from the feeder 
   */
  public CollectHatch() {
    addSequential(new HatchHolderLock(false));
    addSequential(new HatchHolderEject(true));
    addSequential(new HatchHolderLock(true));
    addSequential(new HatchHolderEject(false));
  }
}
