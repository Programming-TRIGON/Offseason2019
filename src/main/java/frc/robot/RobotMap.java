package frc.robot;

/** This class defines the ports of the robot */
public class RobotMap {
    
    // Device ID's for devices connected to the CAN bus
    static class CAN {
        public static final int DRIVETRAIN_LEFT_REAR_MOTOR = 0;
        public static final int DRIVETRAIN_LEFT_MIDDLE_MOTOR = 1;
        public static final int DRIVETRAIN_LEFT_FRONT_MOTOR = 2;
        public static final int DRIVETRAIN_RIGHT_REAR_MOTOR = 3;
        public static final int DRIVETRAIN_RIGHT_MIDDLE_MOTOR = 4;
        public static final int DRIVETRAIN_RIGHT_FRONT_MOTOR = 5;
        public static final int PLACEHOLDER_TALON_LEFT = 6;
        public static final int PLACEHOLDER_TALON_RIGHT = 7;
    }

    // Solenoid ports connected to PCM
    static class PCM {
    }

    // Robot components connected to roboRIO Digital Input Output ports
    static class DIO {
        public static final int DRIVETRAIN_ENCODER_LEFT_CHANNEL_A = 0;
        public static final int DRIVETRAIN_ENCODER_LEFT_CHANNEL_B = 1;
        public static final int DRIVETRAIN_ENCODER_RIGHT_CHANNEL_A = 2;
        public static final int DRIVETRAIN_ENCODER_RIGHT_CHANNEL_B = 3;
    }
}
