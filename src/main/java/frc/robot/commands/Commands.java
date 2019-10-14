
package frc.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
/**
 * this class holds all of the instant commands.
 */
public class Commands {
    public static InstantCommand clearPreferences(){
        InstantCommand c = new InstantCommand(()->Preferences.getInstance().removeAll());
        c.setRunWhenDisabled(true);
        return c;
    }
    
    public static InstantCommand cancelCommand(Command commandToCancel){ 
        return new InstantCommand(commandToCancel::cancel);
    }
}
