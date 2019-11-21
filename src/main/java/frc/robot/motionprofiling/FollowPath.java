package frc.robot.motionprofiling;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Enums.Path;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.utils.MyEncoderFollower;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * This command uses the paths we generated on the path creater and uses it to
 * perform the motion profiling
 */
public class FollowPath extends Command {

  public static final int MULTIPLIER = 10000;
  private MyEncoderFollower right, left;
  private double leftCalculate, rightCalculate, gyroHeading, desiredHeading, angleDifference, turn, angleDiff;
  private SplitTrajectories splitTrajectories;
  private boolean isFlipped = false, isReversed = false;
  private PidSettings pidSettingsLeft = RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT;
  private PidSettings pidSettingsRight = RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT;

  /** This command gets the path number and then follows it */
  public FollowPath(Path path) {
    requires(Robot.drivetrain);
    this.splitTrajectories = new SplitTrajectories(Path.TEST); // splits the path to
    // two sides of the robot.
    right = new MyEncoderFollower(splitTrajectories.getRightTrajectory());
    left = new MyEncoderFollower(splitTrajectories.getLeftTrajectory());
//    try {
//
//      this.right = new EncoderFollower(PathfinderFRC.getTrajectory("output/pathweaver.right"));
//      this.left = new EncoderFollower(PathfinderFRC.getTrajectory("output/pathweaver.left"));
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
  }

  public FollowPath(Path path, boolean isFlipped) {
    this(path);
    this.isFlipped = isFlipped;
  }
  public FollowPath(Path path, PidSettings leftSettings, PidSettings
  rightSettings){
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
    // if (isFlipped) {

    // } else {
    // this.left = new EncoderFollower(splitTrajectories.getLeftTrajectory());
    // this.right = new EncoderFollower(splitTrajectories.getRightTrajectory());
    // }
//    this.left.configureEncoder(Robot.drivetrain.getLeftTicks(),
//        RobotConstants.MotionProfiling.TICKS_PER_REVOLUTION_LEFT, RobotConstants.MotionProfiling.WHEEL_DIAMETER);
//    this.right.configureEncoder(Robot.drivetrain.getRightTicks(),
//        RobotConstants.MotionProfiling.TICKS_PER_REVOLUTION_RIGHT, RobotConstants.MotionProfiling.WHEEL_DIAMETER);
    this.left.configureEncoder(Robot.drivetrain.getLeftDistance());
    this.right.configureEncoder(Robot.drivetrain.getRightDistance());
    this.left.configurePIDVA(pidSettingsLeft.getKP(), 0, pidSettingsLeft.getKD(), pidSettingsLeft.getKV(),
        pidSettingsLeft.getKA());
    this.right.configurePIDVA(RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT.getKP(), 0,
        pidSettingsRight.getKD(), pidSettingsRight.getKV(), pidSettingsRight.getKA());
    // this.left.configurePIDVA(0.5, 0, 0.05, 0.2, 0.05);
    // this.right.configurePIDVA(0.5, 0, 0.05, 0.2, 0.05);
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
    // if (!isReversed)

    double left = (this.leftCalculate /*+ turn + MotionProfiling.KS_LEFT*/) /*/ RobotController.getBatteryVoltage()*/;
    double right = (this.rightCalculate /*- turn + MotionProfiling.KS_RIGHT*/) /*/ RobotController.getBatteryVoltage()*/;
    System.out.println("left: " + leftCalculate + " right: " + rightCalculate);
    Robot.drivetrain.tankDrive(left, right);
    // else
    // Robot.drivetrain.tankDrive(this.leftCalculate + turn, this.rightCalculate -
    // turn);
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
//  public double getError(EncoderFollower encoderFollower){
//    var segment = encoderFollower.getSegment()
//  }
}
