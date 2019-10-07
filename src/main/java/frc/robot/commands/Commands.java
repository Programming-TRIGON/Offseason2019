
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class Commands {
    /**
     * @param tilt the desired state of the tilt
     * @return new instant command that changes the state of the tilt
     */
    public static InstantCommand setTiltCommand(boolean tilt){
        return new InstantCommand(()->Robot.cargoHolder.setTilt(tilt));
    }
}