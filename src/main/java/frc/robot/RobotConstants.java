package frc.robot;

/** This class used to store constants related to the robot */
public class RobotConstants {

    /** Physical measurements of the robot */
    public static class RobotDimensions {
    }

    // TODO: set configure pid settings
    /** All the robot pid values for monitoring */
    public static class RobotPIDSettings {
        public static final PidSettings LIFT_PID_SETTINGS = new PidSettings(0.1, 0.0, 0.0, 5.0, 1.0);
    }

    // TODO: set real values
    /** Constants for sensors on the robot */
    public static class Sensors {
        /** ticks per distance */
        public static final int LIFT_ENCODER_TPD = 42;
        /** height of the lift from the floor */
        public static final int LIFT_ENCODER_OFFSET = 42;
    }
}
