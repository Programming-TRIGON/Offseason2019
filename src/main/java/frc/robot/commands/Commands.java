
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class Commands {
    public static InstantCommand setTiltCommand(boolean tilt){
        return new InstantCommand(()->Robot.cargoHolder.setTilt(tilt));
    }
}
