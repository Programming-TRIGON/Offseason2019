package frc.robot.motionprofiling;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.motionprofiling.Path;
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

  /** This command gets the path number and then follows it */
  public FollowPath(Path path) {
    requires(Robot.drivetrain);
    this.splitTrajectories = new SplitTrajectories(path); // splits the path to two sides of the robot.
    
  }

  @Override
  /** We configure the encoder and the PIDVA */
  protected void initialize() {
    this.left = new EncoderFollower(splitTrajectories.getLeftTrajectory());
    this.right = new EncoderFollower(splitTrajectories.getRightTrajectory());
    this.left.configureEncoder(Robot.drivetrain.getLeftTicks(), RobotConstants.MotionProfiling.TICKS_PER_REVOLUTION_LEFT,
        RobotConstants.MotionProfiling.WHEEL_DIAMETER);
    this.right.configureEncoder(Robot.drivetrain.getRightTicks(), RobotConstants.MotionProfiling.TICKS_PER_REVOLUTION_RIGHT,
        RobotConstants.MotionProfiling.WHEEL_DIAMETER);
    this.left.configurePIDVA(RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT.getKP(), 0,
        RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT.getKD(), RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT.getKV(),
        RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_LEFT.getKA());
    this.right.configurePIDVA(RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT.getKP(), 0,
        RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT.getKD(), RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT.getKV(),
        RobotConstants.MotionProfiling.MOTION_PROFILING_PID_SETTINGS_RIGHT.getKA());
    this.left.configurePIDVA(0.3,0.1,0.5,0.1,0.1);   
    this.right.configurePIDVA(0.3,0.1,0.5,0.1,0.1); 
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
    this.desiredHeading = Pathfinder.r2d(this.left.getHeading());
    this.angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);

    this.angleDifference = this.angleDifference % 360.0;
    if (Math.abs(angleDifference) > 180.0) {
      this.angleDiff = (angleDifference > 0) ? angleDifference - 360 : angleDiff + 360;
    }

    this.turn = RobotConstants.MotionProfiling.MOTION_PROFILING_KP_TURN * (-1.0 / 80.0) * this.angleDifference;

    Robot.drivetrain.tankDrive(this.leftCalculate + turn, this.rightCalculate - turn);
    //Robot.drivetrain.tankDrive(this.leftCalculate, this.rightCalculate);
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
  /**
   * @param pidSettings pid settings for the left follower to be set by testPID
   */
  public void setPID(PidSettings pidSettings){
      this.left.configurePIDVA(pidSettings.getKP(),pidSettings.getKI(),pidSettings.getKD(),pidSettings.getKV(),pidSettings.getKA());
  }
  /**
   * @param pidSettings pid settings for the right follower to be set by testPID
   */
  public void setPID2(PidSettings pidSettings){
    this.right.configurePIDVA(pidSettings.getKP(),pidSettings.getKI(),pidSettings.getKD(),pidSettings.getKV(),pidSettings.getKA());
  }

}
