package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/** This class sets what all of the robot instance components are */
public class RobotComponents {

    public static class CargoFolder {
        public static final DoubleSolenoid FOLDER_SOLENOID = new DoubleSolenoid(RobotMap.CAN.PCM_CAN, RobotMap.PCM.CARGO_FOLDER_SOLENOID_FORWARD, RobotMap.PCM.CARGO_FOLDER_SOLENOID_REVERSE);
    }
}
