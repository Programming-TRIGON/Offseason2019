package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Enums.LiftHeights;
import frc.robot.Enums.Target;
import frc.robot.commands.*;

/**
 * this command puts hatch on the target.
 */
public class PutHatch extends CommandGroup {

    public PutHatch() {
        addSequential(new HatchHolderLock(true));
        addSequential(new FollowTarget(Target.Feeder));
        addSequential(new HatchHolderLock(false));
    }
}
