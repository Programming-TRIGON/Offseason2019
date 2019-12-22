package frc.robot;

/**
 * This class used to store constants related to the robot
 */
public class RobotConstants {
  // TODO: find dimensions

  /**
   * Physical measurements of the robot
   */
  public static class RobotDimensions {

    /**
     * The height which is safe for opening the tilt
     */
    public static final double CARGO_LIFT_OFFSET = 0;
    public static final double HATCH_LIFT_OFFSET = 48;
  }

  // TODO: set configure pid settings

  /**
   * All the robot pid values for monitoring
   */
  public static class RobotPIDSettings {
    public static final PidSettings LIFT_PID_SETTINGS = new PidSettings(0.095, 0, 0.085, 5, 0);
    public static final PidSettings DRIVETRAIN_TURN_PID_SETTINGS = new PidSettings(0, 0, 0, 0, 0);
    public static final PidSettings DRIVE_FORWARD_PID_SETTINGS = new PidSettings(0, 0, 0, 0, 0);
    public static final PidSettings VISION_X_PID_SETTINGS = new PidSettings(0.04575, 0.00015, 0.14, 0, 0.5);
    public static final PidSettings VISION_Y_PID_SETTINGS = new PidSettings(0.0165, 0, 0, 1.15, 0.5); //0.0125
  }

  /**
   * Constants for sensors on the robot
   */
  public static class Sensors {
    public static final double DRIVETRAIN_ENCODERS_DISTANCE_PER_TICKS = 12832;

    //we measure the height according to the bottom of the tilt.
    public static final double LIFT_ENCODER_OFFSET = 0;
    public static final double LIFT_ENCODER_DISTANCE_PER_TICKS = (82968.0 * 2.0) / 100.0;
  }

  public static class MotionProfiling {

    public static final double WHEEL_DIAMETER = 0.1016;
    public static final double WHEEL_BASE_WIDTH = 0.623;
    public static final double TIMEFRAME = 0.02;

    public static final double MAX_VELOCITY = 2.5;
    public static final double MAX_ACCELERATION = 1.5;
    public static final double MAX_JERK = 80;

    public static final PidSettings MOTION_PROFILING_PID_SETTINGS_LEFT = new PidSettings(0.15, 0.1, 2.4, 0.166);
    public static final PidSettings MOTION_PROFILING_PID_SETTINGS_RIGHT = new PidSettings(0.15, 0.1, 2.36, 0.256);
    
    public static final PidSettings MOTION_PROFILING_PID_SETTINGS_LEFT_REVERSE = new PidSettings(0.15, 0.1, 2.36, 0.204);
    public static final PidSettings MOTION_PROFILING_PID_SETTINGS_RIGHT_REVERSE = new PidSettings(0.15, 0.1, 2.36, 0.223);

    public static final double MOTION_PROFILING_KP_TURN = 0.05;
    public static final double MOTION_PROFILING_KP_TURN_REVERSE = 0.05;

    public static final double TICKS_PER_METER_RIGHT = 12832;
    public static final double TICKS_PER_METER_LEFT = 12832;
    public static final int TICKS_PER_REVOLUTION_RIGHT = 4096;
    public static final int TICKS_PER_REVOLUTION_LEFT = 4096;
    public static final double KS_LEFT = 0.219;
    public static final double KS_RIGHT = 0.221;
    public static final double KS_LEFT_REVERSE = 0.167;
    public static final double KS_RIGHT_REVERSE = 0.189;
    //TODO: calibrate using frc characterization.
    public static final double KS = 0;
    public static final double KV = 0;
    public static final double KA = 0;
    public static final double KP = 0;
    public static final double KS_REVERSE = 0;
    public static final double KV_REVERSE = 0;
    public static final double KA_REVERSE = 0;
    public static final double KP_REVERSE = 0;

  }

  public static class Vision {
    public static final double DISTANCE_FROM_TARGET = 0;
    public static final double ANGLE_FROM_TARGET = 0;
  }

  public static class Calibration {
    public static final double leftForwardKv = 0.2103;
    public static final double leftForwardVi = 0.3717;
    public static final double rightForwardKv = 0.1929;
    public static final double rightForwardVi = 0.3913;
  }

  public static class FieldDimensions {
    public static final double PATH2_DISTANCE = 1;
    public static final double PATH3_DISTANCE = 1;
    public static final double HAB_TO_CARGO_SHIP_DISTANCE = 100;
    public static final double AUTO_TURN_ANGLE = 90;
  }
}
