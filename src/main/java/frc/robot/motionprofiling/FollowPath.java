package frc.robot.motionprofiling;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Enums.Path;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * This command uses the paths we generated on the path creater and uses it to
 * perform the motion profiling
 */
public class FollowPath extends Command {

  private EncoderFollower right, left;
  private double leftCalculate, rightCalculate, gyroHeading, desiredHeading, angleDifference, turn, angleDiff;
  private SplitTrajectories splitTrajectories;
  private boolean isFlipped = false, isReversed = false;
  private PidSettings pidSettingsLeft;
  private PidSettings pidSettingsRight;

  /**
   * This command gets the path number and then follows it
   */
  public FollowPath(Path path) {
    requires(Robot.drivetrain);
    this.splitTrajectories = new SplitTrajectories(path); // splits the path to
    // two sides of the robot.
    right = new EncoderFollower(splitTrajectories.getRightTrajectory());
    left = new EncoderFollower(splitTrajectories.getLeftTrajectory());
    pidSettingsLeft = RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT;
    pidSettingsRight = RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT;
  }

  public FollowPath(Path path, boolean isFlipped) {
    this(path);
    this.isFlipped = isFlipped;
  }

  public FollowPath(Path path, PidSettings leftSettings, PidSettings rightSettings) {
    this(path);
    this.pidSettingsLeft = leftSettings;
    this.pidSettingsRight = rightSettings;
  }

  public FollowPath(Path path, boolean isFlipped, boolean isReversed) {
    this(path, isFlipped);
    this.isReversed = isReversed;
  }

  @Override
  /** We configure the encoder and the PIDVA */
  protected void initialize() {
    this.left.configureEncoder(Robot.drivetrain.getLeftTicks(),
            RobotConstants.MotionProfiling.TICKS_PER_REVOLUTION_LEFT, RobotConstants.MotionProfiling.WHEEL_DIAMETER);
    this.right.configureEncoder(Robot.drivetrain.getRightTicks(),
            RobotConstants.MotionProfiling.TICKS_PER_REVOLUTION_RIGHT, RobotConstants.MotionProfiling.WHEEL_DIAMETER);
    this.left.configurePIDVA(pidSettingsLeft.getKP(), 0, pidSettingsLeft.getKD(), pidSettingsLeft.getKV(),
            pidSettingsLeft.getKA());
    this.right.configurePIDVA(pidSettingsRight.getKP(), 0,
            pidSettingsRight.getKD(), pidSettingsRight.getKV(), pidSettingsRight.getKA());
    left.reset();
    right.reset();
    Robot.drivetrain.resetGyro();
  }

  @Override
  /**
   * We calculate the needed power , then we calculate the heading of the gyro to
   * acount for the heading of the robot. The power we give to the motors is the
   * calculation in the beginning - / + the KP.
   */
  protected void execute() {
    leftCalculate = this.left.calculate(Robot.drivetrain.getLeftTicks());
    rightCalculate = this.right.calculate(Robot.drivetrain.getRightTicks());
    gyroHeading = Robot.drivetrain.getAngle();
    desiredHeading = Pathfinder.r2d(this.left.getHeading());
    angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
    angleDifference = this.angleDifference % 360.0;
    if (Math.abs(angleDifference) > 180.0) {
      this.angleDiff = (angleDifference > 0) ? angleDifference - 360 : angleDiff + 360;
    }

    this.turn = RobotConstants.MotionProfiling.MOTION_PROFILING_KP_TURN * (-1.0 / 80.0) * this.angleDifference;

    double left = (this.leftCalculate /*+ turn + MotionProfiling.KS_LEFT*/) / RobotController.getBatteryVoltage();
    double right = (this.rightCalculate /*- turn + MotionProfiling.KS_RIGHT*/) / RobotController.getBatteryVoltage();
    System.out.println("left: " + left + " right: " + right);
    Robot.drivetrain.tankDrive(left, right);
  }

  @Override
  protected boolean isFinished() {
    return this.left.isFinished() && this.right.isFinished();

  }

  @Override
  protected void end() {
    Robot.drivetrain.tankDrive(0, 0);
  }

  @Override
  protected void interrupted() {
    end();
  }

  public void setFlipped(boolean isFlipped) {
    this.isFlipped = isFlipped;
  }

  public void setReversed(boolean isReversed) {
    this.isReversed = isReversed;
  }
}
