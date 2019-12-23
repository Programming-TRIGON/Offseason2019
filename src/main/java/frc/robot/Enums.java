package frc.robot;

import frc.robot.motionprofiling.PathCreator;
import frc.robot.utils.CsvReader;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import wpilibj.geometry.Pose2d;
import wpilibj.geometry.Rotation2d;

import java.io.File;
import java.io.IOException;

/**
 * This class contains enums for the robot, we convert human readable language
 * to the robot language
 */
public class Enums {
  public enum Path {
    RAMP_TO_ROCKET("RampToRocket.csv"),
    BACK_FROM_ROCKET("BackFromRocket"),
    ROCKET_TO_FEEDER("RocketToFeeder.csv"),
    FEEDER_TO_ROCKET("FeederToRocket.csv"),
    TEST_THREE_METERS("three.csv");
    //PATH_PLANNER_ARC("PathPlannerArc.csv"),
    //TEST(new Waypoint[]{new Waypoint(0,0,0), new Waypoint(3,2,0)}),
    //JACI_ARC(new Waypoint[]{new Waypoint(0,0,0), new Waypoint(-1.5,2,Pathfinder.d2r(90))});

    private final Trajectory leftTrajectory;
    private Trajectory rightTrajectory;

    Path(Waypoint[] path) {
      leftTrajectory = Pathfinder.generate(path,
              new Trajectory.Config(Trajectory.FitMethod.HERMITE_QUINTIC, Trajectory.Config.SAMPLES_HIGH,
                      RobotConstants.MotionProfiling.TIMEFRAME, RobotConstants.MotionProfiling.MAX_VELOCITY,
                      RobotConstants.MotionProfiling.MAX_ACCELERATION, RobotConstants.MotionProfiling.MAX_JERK));
    }

    Path(File csvFile) {
      Trajectory trajectory = null;
      try {
        trajectory = Pathfinder.readFromCSV(csvFile);
      } catch (IOException e) {
        System.err.println("File not existing");
      }
      this.leftTrajectory = trajectory;
    }

    /**
     * loads path from a PathPlanner formatted csv file.
     *
     * @param pathPlannerFile name of the file in the paths folder.
     */
    Path(String pathPlannerFile) {
      if (pathPlannerFile.endsWith(".csv"))
        pathPlannerFile = pathPlannerFile.substring(0, pathPlannerFile.length() - 4);
      String pathPlannerFileLeft = pathPlannerFile + "_left.csv";
      String pathPlannerFileRight = pathPlannerFile + "_right.csv";
      leftTrajectory = CsvReader.read("/home/lvuser/paths/" + pathPlannerFileLeft);
      rightTrajectory = CsvReader.read("/home/lvuser/paths/" + pathPlannerFileRight);
    }

    public Trajectory getLeftTrajectory() {
      return leftTrajectory;
    }

    public Trajectory getRightTrajectory() {
      return rightTrajectory;
    }
  }

  public enum Target {
    RocketMiddle(1), RocketSide(0), Feeder(0), CargoShip(0);

    private final int index;

    Target(int index) {
      this.index = index;

    }

    public int getIndex() {
      return index;
    }

  }

  private static double cargoOffset = RobotConstants.RobotDimensions.CARGO_LIFT_OFFSET;
  private static double hatchOffset = RobotConstants.RobotDimensions.HATCH_LIFT_OFFSET;

  public enum LiftHeights {
    RocketCargoTop(212 - cargoOffset), RocketCargoMiddle(141 - cargoOffset), RocketCargoBottom(70 - cargoOffset), RocketHatchTop(190 - hatchOffset), RocketHatchMiddle(119 - RobotConstants.RobotDimensions.HATCH_LIFT_OFFSET),
    HatchBottom(48 - hatchOffset), CargoShip(100 - cargoOffset), Floor(0), Feeder(48 - hatchOffset); // Feeder is the same height as rocket hatch low.
    private double height;

    LiftHeights(double height) {
      this.height = height;
    }

    public double getHeight() {
      return height;
    }
  }

  public enum ScoreHeight {
    kLow, kMedium, kHigh, kMediumGrossman
  }

  public enum RamsetePath {
    THREE_METERS(new Pose2d(0, 0, Rotation2d.fromDegrees(0)), new Pose2d(3, 0, Rotation2d.fromDegrees(0))),
    ARC(new Pose2d(0, 0, Rotation2d.fromDegrees(0)), new Pose2d(2.5, -1, Rotation2d.fromDegrees(-90))),
    RAMP_TO_ROCKET(new Pose2d(0, 0, Rotation2d.fromDegrees(0)), new Pose2d(2.623396042364249, 2.0651822621668573, Rotation2d.fromDegrees(28.652343749999996))),
    BACK_FROM_ROCKET(true, new Pose2d(3.282379856385174, 2.5289245057633853, Rotation2d.fromDegrees(28.300781249999996)), new Pose2d(3.512906827312169, 1.1332474791507943, Rotation2d.fromDegrees(137.28515625))),
    ROCKET_TO_FEEDER(new Pose2d(3.512906827312169, 1.1332474791507943, Rotation2d.fromDegrees(137.28515625)), new Pose2d(0.3634335981791645, 2.8373304156691284, Rotation2d.fromDegrees(174.11132812500003))),
    FEEDER_TO_ROCKET(true, new Pose2d(-1.044983937074417, 2.6828109756503835, Rotation2d.fromDegrees(177.36328125)), new Pose2d(4.029053231536901, 1.7525754603834531, Rotation2d.fromDegrees(166.37695312500003)), new Pose2d(5.8037309773561665, 2.3106970679689245, Rotation2d.fromDegrees(148.2275390625))),
    BACK_FROM_FAR_SIDE(true, new Pose2d(4.97321554891652, 2.790741747396888, Rotation2d.fromDegrees(146.689453125)), new Pose2d(5.379935531237157, 2.4028534888525934, Rotation2d.fromDegrees(90.62402343750003)), new Pose2d(3.974053846730245, 1.6916910223164185, Rotation2d.fromDegrees(-1.4062500000000002)));

    private final wpilibj.trajectory.Trajectory trajectory;

    RamsetePath(Pose2d... waypoints) {
      var creator = new PathCreator(false);
      trajectory = creator.generatePath(waypoints);
    }

    RamsetePath(boolean isReversed, Pose2d... waypoints) {
      var creator = new PathCreator(isReversed);
      trajectory = creator.generatePath(waypoints);
    }

    public wpilibj.trajectory.Trajectory getTrajectory() {
      return trajectory;
    }
  }
}
