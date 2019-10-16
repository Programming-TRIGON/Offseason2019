package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Enums.LiftHeights;
import frc.robot.Enums.Target;
import frc.robot.commands.*;

/**
 * this command collects hatch from the feeder.
 */
public class CollectHatchFromFeeder extends CommandGroup {

    public CollectHatchFromFeeder() {
        addSequential(new HatchHolderLock(false));
        SetLiftHeight lowerLiftCommand = new SetLiftHeight(LiftHeights.Floor);
        addParallel(lowerLiftCommand);
        addSequential(new WaitCommand(0.02));
        addSequential(new VisionPID(Target.Feeder, true));
        addSequential(new WaitUntil(lowerLiftCommand::isOnTarget));
        addSequential(new HatchHolderLock(true));
    }
}
