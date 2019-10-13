
package frc.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
/**
 * this class holds all of the instant commands.
 */
public class Commands {
    public static InstantCommand clearPreferences(){
        InstantCommand c = new InstantCommand(()->Preferences.getInstance().removeAll());
        c.setRunWhenDisabled(true);
        return c;
    }

}
