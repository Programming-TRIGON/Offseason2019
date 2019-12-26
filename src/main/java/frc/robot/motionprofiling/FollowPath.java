package frc.robot.motionprofiling;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.RobotConstants.MotionProfiling;
import frc.robot.enums.Path;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * This command uses the paths we generated on the path creater and uses it to
 * perform the motion profiling
 */
public class FollowPath extends Command {

  private EncoderFollower right, left;
  private double leftCalculate, rightCalculate, gyroHeading, desiredHeading, angleDifference, turn, angleDiff;
  private boolean isFlipped = false;
  private PidSettings pidSettingsLeft;
  private PidSettings pidSettingsRight;
  private Path path;
  private double turnKp;
  private double ksLeft;
  private double ksRight;
  private double startAngle;
  private Notifier notifier;

  /**
   * This command gets the path number and then follows it
   */
  public FollowPath(Path path) {
    requires(Robot.drivetrain);
    this.path = path;
    left = new EncoderFollower(path.getRightTrajectory());
    right = new EncoderFollower(path.getLeftTrajectory());
    pidSettingsLeft = RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT;
    pidSettingsRight = RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT;
    turnKp = MotionProfiling.MOTION_PROFILING_KP_TURN;
    ksLeft = MotionProfiling.KS_LEFT;
    ksRight = MotionProfiling.KS_RIGHT;
    notifier = new Notifier(this::executeFollow);
  }

  public FollowPath(Path path, boolean isFlipped) {
    this(path);
    setFlipped(isFlipped);
  }

  public FollowPath(Path path, PidSettings leftSettings, PidSettings rightSettings, double turnKp) {
    this(path);
    pidSettingsLeft = leftSettings;
    pidSettingsRight = rightSettings;
    this.turnKp = turnKp;
  }

  public FollowPath(Path path, boolean isFlipped, boolean isReversed) {
    this(path, isFlipped);
    setReversed(isReversed);
  }

  @Override
  /** We configure the encoder and the PIDVA */
  protected void initialize() {
    left.configureEncoder(Robot.drivetrain.getLeftTicks(), RobotConstants.MotionProfiling.TICKS_PER_REVOLUTION_LEFT,
        RobotConstants.MotionProfiling.WHEEL_DIAMETER);
    right.configureEncoder(Robot.drivetrain.getRightTicks(), RobotConstants.MotionProfiling.TICKS_PER_REVOLUTION_RIGHT,
        RobotConstants.MotionProfiling.WHEEL_DIAMETER);
    left.configurePIDVA(pidSettingsLeft.getKP(), 0, pidSettingsLeft.getKD(), pidSettingsLeft.getKV(),
        pidSettingsLeft.getKA());
    right.configurePIDVA(pidSettingsRight.getKP(), 0, pidSettingsRight.getKD(), pidSettingsRight.getKV(),
        pidSettingsRight.getKA());
    startAngle = Pathfinder.d2r(Robot.drivetrain.getAngle());
    left.reset();
    right.reset();
    notifier.startPeriodic(MotionProfiling.TIMEFRAME);
  }

  /**
   * We calculate the needed power , then we calculate the heading of the gyro to
   * acount for the heading of the robot. The power we give to the motors is the
   * calculation in the beginning - / + the KP.
   */

  private void executeFollow() {
    leftCalculate = left.calculate(Robot.drivetrain.getLeftTicks());
    rightCalculate = right.calculate(Robot.drivetrain.getRightTicks());
    gyroHeading = Robot.drivetrain.getAngle();
    if (isFlipped)
      desiredHeading = startAngle - (Pathfinder.r2d(left.getHeading()) - startAngle);
    else
      desiredHeading = Pathfinder.r2d(left.getHeading());
    angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
    angleDifference = angleDifference % 360.0;
    if (Math.abs(angleDifference) > 180.0) {
      angleDiff = (angleDifference > 0) ? angleDifference - 360 : angleDiff + 360;
    }
    turn = turnKp * (-1.0 / 80.0) * angleDifference;
    double left = 1 * ((leftCalculate + turn + ksLeft) / RobotController.getBatteryVoltage());
    double right = 1 * ((rightCalculate - turn + ksRight) / RobotController.getBatteryVoltage());
    Robot.drivetrain.tankDrive(right, left);
  }

  @Override
  protected boolean isFinished() {
    return left.isFinished() && right.isFinished();
  }

  @Override
  protected void end() {
    notifier.stop();
    Robot.drivetrain.tankDrive(0, 0);
  }

  @Override
  protected void interrupted() {
    end();
  }

  public void setFlipped(boolean isFlipped) {
    if (!isFlipped) {
      left.setTrajectory(path.getLeftTrajectory());
      right.setTrajectory(path.getRightTrajectory());
    } else {
      left.setTrajectory(path.getRightTrajectory());
      right.setTrajectory(path.getLeftTrajectory());
    }
    this.isFlipped = isFlipped;
  }

  public void setReversed(boolean isReversed) {
    if (isReversed) {
      pidSettingsLeft = RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT_REVERSE;
      pidSettingsRight = RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT_REVERSE;
      ksLeft = -MotionProfiling.KS_LEFT_REVERSE;
      ksRight = -MotionProfiling.KS_RIGHT_REVERSE;
    } 
    // else {
    //   pidSettingsLeft = RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT;
    //   pidSettingsRight = RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT;
    //   ksLeft = MotionProfiling.KS_LEFT;
    //   ksRight = MotionProfiling.KS_RIGHT;
    // }
  }
}
