
package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Enums;
import frc.robot.Enums.LiftHeights;
import frc.robot.commands.*;

/**
 * this command collects hatch from the feeder.
 */
public class CollectHatchFromFeeder extends CommandGroup {

    public CollectHatchFromFeeder() {
        SetLiftHeight lowerLiftCommand = new SetLiftHeight(LiftHeights.HatchBottom);
        addParallel(lowerLiftCommand);
        addSequential(new VisionPID(Enums.Target.Feeder, true));
        addSequential(new WaitUntil(lowerLiftCommand::isOnTarget));
        addSequential(new CollectHatch());
    }
}
