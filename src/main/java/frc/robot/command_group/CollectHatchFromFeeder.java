package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Enums.LiftHeights;
import frc.robot.commands.*;

/**
 * this command collects hatch from the feeder.
 */
public class CollectHatchFromFeeder extends CommandGroup {

    public CollectHatchFromFeeder() {
        SetLiftHeight lowerLiftCommand = new SetLiftHeight(LiftHeights.Floor);
        addParallel(lowerLiftCommand);
        //addSequential(new VisionPID(Target.Feeder, true));
        //addSequential(new WaitUntil(lowerLiftCommand::isOnTarget));
        addSequential(new HatchHolderLock(false));
    }
}
