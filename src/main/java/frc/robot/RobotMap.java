package frc.robot;

/** This class defines the ports of the robot */
public class RobotMap {
    
    // Device ID's for devices connected to the CAN bus
    static class CAN {
        public static final int PCM_CAN = 1;
    }

    // Solenoid ports connected to PCM
    static class PCM {
        public static final int CARGO_FOLDER_SOLENOID_FORWARD = 0; 
        public static final int CARGO_FOLDER_SOLENOID_REVERSE = 1;
    }

    // Robot components connected to roboRIO Digital Input Output ports
    static class DIO {
    }
}
