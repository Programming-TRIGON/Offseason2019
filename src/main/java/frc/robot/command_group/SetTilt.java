package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Commands;
import frc.robot.commands.HatchHolderEject;

public class SetTilt extends CommandGroup {
    /**
     * @param tilt the desired state of the tilt
     * this is like the instant command but with safety.
     */
    public SetTilt(boolean tilt){
        addSequential(new HatchHolderEject(true));
        addSequential(Commands.setTiltCommand(tilt));
    }
}
