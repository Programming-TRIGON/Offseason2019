package frc.robot.utils;

import jaci.pathfinder.Trajectory;


public class MyEncoderFollower {

  double encoder_offset;

  double kp, ki, kd, kv, ka;

  double last_error, heading;

  int segment;
  Trajectory trajectory;

  public MyEncoderFollower(Trajectory trajectory) {
    this.trajectory = trajectory;
  }


  /**
   * Set a new trajectory to follow, and reset the cumulative errors and segment counts
   *
   * @param trajectory a previously generated trajectory
   */
  public void setTrajectory(Trajectory trajectory) {
    this.trajectory = trajectory;
    reset();
  }

  /**
   * Configure the PID/VA Variables for the Follower
   *
   * @param kp The proportional term. This is usually quite high (0.8 - 1.0 are common values)
   * @param ki The integral term. Currently unused.
   * @param kd The derivative term. Adjust this if you are unhappy with the tracking of the follower. 0.0 is the default
   * @param kv The velocity ratio. This should be 1 over your maximum velocity @ 100% throttle.
   *           This converts m/s given by the algorithm to a scale of -1..1 to be used by your
   *           motor controllers
   * @param ka The acceleration term. Adjust this if you want to reach higher or lower speeds faster. 0.0 is the default
   */
  public void configurePIDVA(double kp, double ki, double kd, double kv, double ka) {
    this.kp = kp;
    this.ki = ki;
    this.kd = kd;
    this.kv = kv;
    this.ka = ka;
  }

  /**
   * Configure the Encoders being used in the follower.
   *
   * @param initial_position The initial 'offset' of your encoder. This should be set to the encoder value just
   *                         before you start to track
   */
  public void configureEncoder(double initial_position) {
    encoder_offset = initial_position;
  }

  /**
   * Reset the follower to start again. Encoders must be reconfigured.
   */
  public void reset() {
    last_error = 0;
    segment = 0;
  }

  /**
   * Calculate the desired output for the motors, based on the amount of ticks the encoder has gone through.
   * This does not account for heading of the robot. To account for heading, add some extra terms in your control
   * loop for realignment based on gyroscope input and the desired heading given by this object.
   *
   * @param encoder_tick The amount of ticks the encoder has currently measured.
   * @return The desired output for your motor controller
   */
  public double calculate(int encoder_tick) {
    double distanceCovered = encoder_tick - encoder_offset;
    if (segment < trajectory.length()) {
      Trajectory.Segment seg = trajectory.get(segment);
      double error = seg.position - distanceCovered;
      double calculated_value =
              kp * error +                                    // Proportional
                      kd * ((error - last_error) / seg.dt) +          // Derivative
                      (kv * seg.velocity + ka * seg.acceleration);    // V and A Terms
      last_error = error;
      heading = seg.heading;
      segment++;

      return calculated_value;
    } else return 0;
  }

  /**
   * @return the desired heading of the current point in the trajectory
   */
  public double getHeading() {
    return heading;
  }

  /**
   * @return the current segment being operated on
   */
  public Trajectory.Segment getSegment() {
    return trajectory.get(segment);
  }

  /**
   * @return whether we have finished tracking this trajectory or not.
   */
  public boolean isFinished() {
    return segment >= trajectory.length();
  }

}

