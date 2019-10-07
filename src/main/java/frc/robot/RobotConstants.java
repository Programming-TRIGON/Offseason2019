package frc.robot;

/** This class used to store constants related to the robot */
public class RobotConstants {

    /** Physical measurements of the robot */
    public static class RobotDimensions {
        //TODO: set real values
        public static final double DISTANCE_FROM_MIDDLE_TO_LIMELIGHT = 5934;
    }

    /** All the robot pid values for monitoring */
    public static class RobotPIDSettings {
        public static final PidSettings DRIVETRAIN_TURN_PID_SETTINGS = new PidSettings(0, 0, 0, 0, 0);
        public static final PidSettings VISION_X_PID_SETTINGS = new PidSettings(0, 0, 0, 0, 0);
        public static final PidSettings VISION_Y_PID_SETTINGS = new PidSettings(0, 0, 0, 0, 0);
    }

    /** Constants for sensors on the robot */
    public static class Sensors {
        public static final double DRIVETRAIN_ENCODERS_DISTANCE_PER_TICKS = 4096;

        public static final double LIFT_ENCODER_OFFSET = 4096;
        public static final double LIFT_ENCODER_DISTANCE_PER_TICKS = 4096; 
    }

    public static class MotionProfiling {
        
    public static final double WHEEL_DIAMETER = 0.1524;
    public static final double WHEEL_BASE_WIDTH = 0.6;
    public static final double TIMEFRAME = 0.02;

    public static final double MAX_VELOCITY = 3;
    public static final double MAX_ACCELERATION = 2;
    public static final double MAX_JERK = 80;

    public static final PidSettings MOTION_PROFILING_PID_SETTINGS_LEFT = new PidSettings(0.0, 0, 0.0, 0.0);//0.2103, 0.5139
    public static final PidSettings MOTION_PROFILING_PID_SETTINGS_RIGHT = new PidSettings(0.0, 0, 0.0, 0.0);//0.1929, 2.5147

    public static final double MOTION_PROFILING_KP_TURN = 0.0025;

    public static final double TICKS_PER_METER_RIGHT = 575.0;
    public static final double TICKS_PER_METER_LEFT = 771.5;
    public static final int TICKS_PER_REVOLUTION_RIGHT = 290;
    public static final int TICKS_PER_REVOLUTION_LEFT = 360;
    }
    
    public class Vision{
    }

    public class Calibration{
        public static final double leftForwardKv = 0.2103;
        public static final double leftForwardVi = 0.3717;
        public static final double rightForwardKv = 0.1929;
        public static final double rightForwardVi = 0.3913;
    }
}
