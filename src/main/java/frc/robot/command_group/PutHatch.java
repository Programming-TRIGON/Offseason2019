package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Enums.Target;
import frc.robot.commands.*;

/**
 * this command puts \ collects hatch on \ from the target.
 */
public class PutHatch extends CommandGroup {
    public PutHatch(boolean put) {
        addSequential(new HatchHolderLock(put));
        addSequential(new FollowTarget(Target.Feeder));
        addSequential(new HatchHolderLock(!put));
        addSequential(new AfterHatchPlacement());
    }
}
