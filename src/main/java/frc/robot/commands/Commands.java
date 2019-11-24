
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.RobotComponents;
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

    public static InstantCommand stopCompressor() {
        InstantCommand c = new InstantCommand(RobotComponents.compressor::stop);
        c.setRunWhenDisabled(true);
        return c;
    }
    
    public static InstantCommand startCompressor() {
        InstantCommand c = new InstantCommand(RobotComponents.compressor::start);
        c.setRunWhenDisabled(true);
        return c;
    }

    public static InstantCommand resetEncoders() {
        InstantCommand c = new InstantCommand(Robot.drivetrain::resetEncoders);
        c.setRunWhenDisabled(true);
        return c;
    }

    public static InstantCommand calibrateGyro() {
        InstantCommand c = new InstantCommand(Robot.drivetrain::calibrateGyro);
        c.setRunWhenDisabled(true);
        return c;
    }

    public static InstantCommand toggleDrive() {
        InstantCommand c = new InstantCommand(() -> DriveArcade.toggleDrive());
        return c;
    }
}
