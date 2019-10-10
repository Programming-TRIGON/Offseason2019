
package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Enums;
import frc.robot.Enums.LiftHeights;
import frc.robot.commands.HatchHolderEject;
import frc.robot.commands.HatchHolderLock;
import frc.robot.commands.VisionPID;
import frc.robot.commands.setLiftHeight;
/**
 * this command collects hatch from the feeder.
 */
public class CollectHatchFromFeeder extends CommandGroup {

  public CollectHatchFromFeeder() {
    addParallel(new VisionPID(Enums.Target.Feeder,true));
    addSequential(new setLiftHeight(LiftHeights.Feeder));
    addSequential(new HatchHolderLock(false));
    addSequential(new HatchHolderEject(true));
    addSequential(new HatchHolderLock(true));
    addSequential(new HatchHolderEject(false));
    
  }
}
