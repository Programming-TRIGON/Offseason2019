package frc.robot.motionprofiling;

import frc.robot.Robot;
import frc.robot.RobotConstants.MotionProfiling;
import wpilibj.geometry.Pose2d;
import wpilibj.trajectory.Trajectory;
import wpilibj.trajectory.TrajectoryConfig;
import wpilibj.trajectory.TrajectoryGenerator;

import java.util.Arrays;

/**
 * This class generates all the paths for the path follower command to use.
 */
public class PathCreator {

  private final TrajectoryConfig config;

  /**
   * We configure the pathfinding and create new pathes, then we generate all the
   * pathes we created
   */
  public PathCreator() {
    config = new TrajectoryConfig(MotionProfiling.MAX_VELOCITY, MotionProfiling.MAX_ACCELERATION)
            .setKinematics(Robot.drivetrain.getKinematics());
  }

  /**
   * Writes all the paths to csv for quick generation of paths
   */
  public void writeToCSV_AllPaths() {
    // for (Path path : Path.values()) {
    //     String s = "/home/lvuser/Paths " + path.name() + ".csv";
    //     Pathfinder.writeToCSV(new File(s), path.getLeftTrajectory());
    // }
  }

  public Trajectory generatePath(Pose2d... waypoints) {
    return TrajectoryGenerator.generateTrajectory(Arrays.asList(waypoints), config);
  }

}
