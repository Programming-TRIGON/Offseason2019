
package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Enums;
import frc.robot.Enums.LiftHeights;
import frc.robot.Robot;
import frc.robot.commands.*;

/**
 * this command collects hatch from the feeder.
 */
public class CollectHatchFromFeeder extends CommandGroup {

  public CollectHatchFromFeeder() {
    addParallel(new VisionPID(Enums.Target.Feeder,true));
    setLiftHeight lowerLiftCommand = new setLiftHeight(LiftHeights.HatchBottom);
    addParallel(lowerLiftCommand);
    addSequential(new WaitUntil(lowerLiftCommand::isOnTarget));
    addSequential(new HatchHolderLock(false));
    addSequential(new HatchHolderEject(true));
    addSequential(new HatchHolderLock(true));
    addSequential(new HatchHolderEject(false));
    
  }
}
