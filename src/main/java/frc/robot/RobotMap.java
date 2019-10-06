package frc.robot;

/** This class defines the ports of the robot */
public class RobotMap {
    // TODO: add real values
    // Device ID's for devices connected to the CAN bus
    static class CAN {
        public static final int LIFT_MOTOR_FRONT = 0;
        public static final int LIFT_MOTOR_REAR = 1;
    }

    // Solenoid ports connected to PCM
    static class PCM {
        public static final int HATCH_HOLDER_LOCK_SOLENOID_FORWARD = 2;
        public static final int HATCH_HOLDER_LOCK_SOLENOID_REVERSE = 3;

        public static final int HATCH_HOLDER_EJECT_SOLENOID_FORWARD = 4;
        public static final int HATCH_HOLDER_EJECT_SOLENOID_REVERSE = 5; 
    }

    // Robot components connected to roboRIO Digital Input Output ports
    static class DIO {
        public static final int LIFT_SWITCH_BOTTOM = 2;
        public static final int LIFT_SWITCH_TOP = 3;
    }
}
