
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
/**
 * this class holds all of the instant commands.
 */
public class Commands {
    
    public static InstantCommand cancelCommand(Command commandToCancel){ 
        if(commandToCancel != null)
            return new InstantCommand(commandToCancel::cancel);
        return null;
    }
    

    public static InstantCommand setRunWhenDisabled(Runnable runnable){
        InstantCommand c =  new InstantCommand(runnable);
        c.setRunWhenDisabled(true);
        return c;
    }
}
