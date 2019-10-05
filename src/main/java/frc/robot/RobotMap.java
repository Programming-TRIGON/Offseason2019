package frc.robot;

/** This class defines the ports of the robot */
public class RobotMap {
    // TODO: add real values
    // Device ID's for devices connected to the CAN bus
    static class CAN {
        public static final int CARGO_HOLDER_MOTOR = 0;
        public static final int CARGO_COLLECTOR_MOTOR = 1;
    }

    // Solenoid ports connected to PCM
    static class PCM {
    }

    // Robot components connected to roboRIO Digital Input Output ports
    static class DIO {
        public static final int HOLDER_SWITCH = 0;
    }
}
