package frc.robot;

/** This class used to store constants related to the robot */
public class RobotConstants {

    /** Physical measurements of the robot */
    public static class RobotDimensions {
    }

    /** All the robot pid values for monitoring */
    public static class RobotPIDSettings {
    }

    // TODO: set real values
    /** Constants for sensors on the robot */
    public static class Sensors {
        // distance per pulse.
        public static double TILT_DISTANCE_PER_PULSE = 1.0;
        /** ticks per distance */
        public static final int LIFT_ENCODER_TPD = 42;
        /** height of the lift from the floor */
        public static final int LIFT_ENCODER_OFFSET = 42;
    }
}
