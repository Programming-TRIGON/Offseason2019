package frc.robot.motionprofiling;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.Enums.Path;
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

  /** This command gets the path number and then follows it */
  public FollowPath(Path path) {
    requires(Robot.drivetrain);
    this.splitTrajectories = new SplitTrajectories(path); // splits the path to two sides of the robot.
    
  }

  public FollowPath(Path path, boolean isFlipped) {
    this(path);
    this.isFlipped = isFlipped;
  }

  public FollowPath(Path path, boolean isFlipped, boolean isReversed) {
    this(path, isFlipped);
    this.isReversed = isReversed;
  }

  @Override
  /** We configure the encoder and the PIDVA */
  protected void initialize() {
    if (isFlipped) {
      this.right = new EncoderFollower(splitTrajectories.getLeftTrajectory());
      this.left = new EncoderFollower(splitTrajectories.getRightTrajectory());
    } else {
      this.left = new EncoderFollower(splitTrajectories.getLeftTrajectory());
      this.right = new EncoderFollower(splitTrajectories.getRightTrajectory());
    }
    this.left.configureEncoder(Robot.drivetrain.getLeftTicks(),
        RobotConstants.MotionProfiling.TICKS_PER_REVOLUTION_LEFT, RobotConstants.MotionProfiling.WHEEL_DIAMETER);
    this.right.configureEncoder(Robot.drivetrain.getRightTicks(),
        RobotConstants.MotionProfiling.TICKS_PER_REVOLUTION_RIGHT, RobotConstants.MotionProfiling.WHEEL_DIAMETER);
    this.left.configurePIDVA(RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT.getKP(), 0,
        RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT.getKD(),
        RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT.getKV(),
        RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT.getKA());
    this.right.configurePIDVA(RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT.getKP(), 0,
        RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT.getKD(),
        RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT.getKV(),
        RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT.getKA());
    this.left.configurePIDVA(0.5, 0, 0.05, 0.2, 0.05);
    this.right.configurePIDVA(0.5, 0, 0.05, 0.2, 0.05);
    Robot.drivetrain.resetGyro();
  }

  @Override
  /**
   * We calculate the needed power , then we calculate the heading of the gyro to
   * acount for the heading of the robot. The power we give to the motors is the
   * calculation in the beginning - / + the KP.
   */
  protected void execute() {
    this.leftCalculate = this.left.calculate(Robot.drivetrain.getLeftTicks());
    this.rightCalculate = this.right.calculate(Robot.drivetrain.getRightTicks());

    this.gyroHeading = Robot.drivetrain.getAngle();
    if (isFlipped)
      this.desiredHeading = -Pathfinder.r2d(this.left.getHeading());
    else
      this.desiredHeading = Pathfinder.r2d(this.left.getHeading());
    this.angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);

    this.angleDifference = this.angleDifference % 360.0;
    if (Math.abs(angleDifference) > 180.0) {
      this.angleDiff = (angleDifference > 0) ? angleDifference - 360 : angleDiff + 360;
    }

    this.turn = RobotConstants.MotionProfiling.MOTION_PROFILING_KP_TURN * (-1.0 / 80.0) * this.angleDifference;
    if (!isReversed)
      Robot.drivetrain.tankDrive(-0.6*(this.leftCalculate + turn), -0.6*(this.rightCalculate - turn));
    else
      Robot.drivetrain.tankDrive(this.leftCalculate + turn, this.rightCalculate - turn);
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
