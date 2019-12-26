package frc.robot.enums;

import java.io.File;
import java.io.IOException;

import frc.robot.RobotConstants;
import frc.robot.utils.CsvReader;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

/**
 * Motion profiling path enum and creating 
 */
public enum Path {
    RAMP_TO_ROCKET("RampToRocket_left.csv", "RampToRocket_right.csv"),
    BACK_FROM_ROCKET("BackFromRocket_left.csv", "BackFromRocket_right.csv"),
    ROCKET_TO_FEEDER("RocketToFeeder_left.csv", "RocketToFeeder_right.csv"),
    FEEDER_TO_ROCKET("FeederToRocket_left.csv", "FeederToRocket_right.csv"),
    TEST_THREE_METERS("three_left.csv","three_right.csv");
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
    Path(String pathPlannerFileLeft, String pathPlannerFileRight) {
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
    
