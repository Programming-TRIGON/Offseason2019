package frc.robot;

import frc.robot.motionprofiling.PathCreator;
import frc.robot.utils.CsvReader;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import wpilibj.geometry.Pose2d;
import wpilibj.geometry.Rotation2d;
import wpilibj.geometry.Transform2d;
import wpilibj.geometry.Translation2d;

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
    THREE_METERS(waypoint(0, 0, 0), waypoint(3, 0, 0)),
    ARC(waypoint(0, 0, 0), waypoint(2.5, -1, -90)),
    RAMP_TO_ROCKET(waypoint(0, 0, 0), waypoint(2.623396042364249, 2.0051822621668573, 30)),
    BACK_FROM_ROCKET(true, waypoint(3.282379856385174, 2.5289245057633853, 28.300781249999996), waypoint(3.512906827312169, 1.1332474791507943, 137.28515625)),
    ROCKET_TO_FEEDER(waypoint(3.512906827312169, 1.1332474791507943, 137.28515625), waypoint(0.3634335981791645, 2.8373304156691284, 174.11132812500003)),
    FEEDER_TO_ROCKET(true, waypoint(-1.044983937074417, 2.6828109756503835, 177.36328125), waypoint(4.029053231536901, 1.7525754603834531, 166.37695312500003), waypoint(5.8037309773561665, 2.3106970679689245, 148.2275390625)),
    BACK_FROM_FAR_SIDE(true, waypoint(4.97321554891652, 2.790741747396888, 146.689453125), waypoint(5.379935531237157, 2.4028534888525934, 90.62402343750003), waypoint(3.974053846730245, 1.6916910223164185, -1.4062500000000002));

    private static Pose2d waypoint(double x, double y, double theta) {
      return new Pose2d(x, y, Rotation2d.fromDegrees(theta));
    }

    private final wpilibj.trajectory.Trajectory trajectory;

    RamsetePath(Pose2d... waypoints) {
      this(false, waypoints);
    }

    RamsetePath(boolean isReversed, Pose2d... waypoints) {
      var creator = new PathCreator(isReversed);
      System.out.println(this.name());
      trajectory = creator.generatePath(waypoints).transformBy(new Transform2d(new Translation2d(1.58,0.70),Rotation2d.fromDegrees(0)));
      for (Pose2d waypoint: waypoints) {
        Pose2d transformed = waypoint.transformBy(new Transform2d(new Translation2d(1.58, 0.70), Rotation2d.fromDegrees(0)));
        System.out.println(transformed.getTranslation().getX()+", " +transformed.getTranslation().getY());
      }
    }

    public wpilibj.trajectory.Trajectory getTrajectory() {
      return trajectory;
    }
  }
}
