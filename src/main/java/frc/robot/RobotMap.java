package frc.robot;

/** This class defines the ports of the robot */
public class RobotMap {
    // TODO: add real values
    // Device ID's for devices connected to the CAN bus
    static class CAN {
        public static int TILT_MOTOR = 0;
        public static final int LIFT_MOTOR_FRONT = 0;
        public static final int LIFT_MOTOR_REAR = 1;
    }

    // Solenoid ports connected to PCM
    static class PCM {
    }
    
    //TODO: set real values.
    // Robot components connected to roboRIO Digital Input Output ports
    static class DIO {
        
        public static int ENCODER_A = 0;
        public static int ENCODER_B = 0;
        public static final int LIFT_SWITCH_BOTTOM = 2;
        public static final int LIFT_SWITCH_TOP = 3;
    }
}
