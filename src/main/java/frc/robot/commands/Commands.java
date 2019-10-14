
package frc.robot.commands;

import com.spikes2212.dashboard.ConstantHandler;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
/**
 * this class holds all of the instant commands.
 */
public class Commands {
    
    public static InstantCommand cancelCommand(Command commandToCancel){ 
        return new InstantCommand(commandToCancel::cancel);
    }
    

    public static InstantCommand setRunWhenDisabled(Runnable runnable){
        InstantCommand c =  new InstantCommand(runnable);
        c.setRunWhenDisabled(true);
        return c;
    }
}
