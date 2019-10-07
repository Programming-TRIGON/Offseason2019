package frc.robot;

/** This class defines the ports of the robot */
public class RobotMap {

    // Device ID's for devices connected to the CAN bus
    static class CAN {
        public static final int CARGO_HOLDER_MOTOR = 6;

        public static final int DRIVETRAIN_LEFT_REAR_MOTOR = 3;
        public static final int DRIVETRAIN_LEFT_MIDDLE_MOTOR = 4;
        public static final int DRIVETRAIN_LEFT_FRONT_MOTOR = 5;
        public static final int DRIVETRAIN_RIGHT_REAR_MOTOR = 7;
        public static final int DRIVETRAIN_RIGHT_MIDDLE_MOTOR = 8;
        public static final int DRIVETRAIN_RIGHT_FRONT_MOTOR = 9;

        public static final int LIFT_MOTOR_FRONT = 1;
        public static final int LIFT_MOTOR_REAR = 2;
    }

    // Solenoid ports connected to PCM
    static class PCM {
        public static final int TILT_SOLENOID_FORWARD = 0;
        public static final int TILT_SOLENOID_REVERSE = 1;

        public static final int HATCH_HOLDER_LOCK_SOLENOID_FORWARD = 1;
        public static final int HATCH_HOLDER_LOCK_SOLENOID_REVERSE = 2;
        public static final int HATCH_HOLDER_EJECT_SOLENOID_FORWARD = 3;
        public static final int HATCH_HOLDER_EJECT_SOLENOID_REVERSE = 4; 
    }

    // Robot components connected to roboRIO Digital Input Output ports
    static class DIO {
        public static final int HOLDER_SWITCH = 0;

        public static final int DRIVETRAIN_ENCODER_LEFT_CHANNEL_A = 0;
        public static final int DRIVETRAIN_ENCODER_LEFT_CHANNEL_B = 1;
        public static final int DRIVETRAIN_ENCODER_RIGHT_CHANNEL_A = 2;
        public static final int DRIVETRAIN_ENCODER_RIGHT_CHANNEL_B = 3;
        
        public static final int LIFT_SWITCH_TOP = 0;
        public static final int LIFT_SWITCH_BOTTOM = 1;
    }
}
