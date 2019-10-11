
package frc.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
/**
 * this class holds all of the instant commands.
 */
public class Commands {
    /**
     * @param tilt the desired state of the tilt
     * @return new instant command that changes the state of the tilt
     */
    public static InstantCommand setTiltCommand(boolean tilt){
        return new InstantCommand(()->Robot.cargoHolder.setTilt(tilt));
    }
    public static InstantCommand clearPreferences(){
        InstantCommand c = new InstantCommand(()->Preferences.getInstance().removeAll());
        c.setRunWhenDisabled(true);
        return c;
    }

}
