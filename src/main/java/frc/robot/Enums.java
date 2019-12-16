package frc.robot;

import java.io.IOException;
import java.io.File;

import frc.robot.utils.CsvReader;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

/**
 * This class contains enums for the robot, we convert human readable language
 * to the robot language
 */
public class Enums {
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
    
    public enum Target {
        RIGHT_CARGOSHIP(90.0), LEFT_CARGOSHIP(-90.0), LEFT_ROCKET_CARGO(90), RIGHT_ROCKET_CARGO(-90),
        CLOSE_LEFT_ROCKET(28.75), FAR_LEFT_ROCKET(151.25), CLOSE_RIGHT_ROCKET(-28.75), FAR_RIGHT_ROCKET(-151.25),
        CARGO_SHIP_FRONT(0), FEEDER(180);

        private double absoluteAngle;

        Target(double absoluteAngle) {
            this.absoluteAngle = absoluteAngle;

        }
    
        public int getIndex() {
            return 0;
        }

        public double getAbsoluteAngle() {
            return absoluteAngle;
        }
    }

    private static double cargoOffset = RobotConstants.RobotDimensions.CARGO_LIFT_OFFSET;
    private static double hatchOffset = RobotConstants.RobotDimensions.HATCH_LIFT_OFFSET;
    
    public enum LiftHeights {
        RocketCargoTop(212-cargoOffset), RocketCargoMiddle(141-cargoOffset), RocketCargoBottom(70-cargoOffset), RocketHatchTop(190-hatchOffset), RocketHatchMiddle(119-RobotConstants.RobotDimensions.HATCH_LIFT_OFFSET),
        HatchBottom(48-hatchOffset), CargoShip(100-cargoOffset), Floor(0), Feeder(48-hatchOffset); // Feeder is the same height as rocket hatch low.
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
}
