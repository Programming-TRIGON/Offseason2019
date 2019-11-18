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
        RAMP_TO_ROCKET("RampToRocket.csv"), TEST(new Waypoint[]{new Waypoint(0,0,0), new Waypoint(3,2,0)})
        ,WEAVER(new File("/home/lvuser/paths/pathweaver.csv"));

        private final Trajectory trajectory;

        Path(Waypoint[] path) {
            trajectory = Pathfinder.generate(path,
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
            this.trajectory = trajectory;

        }

        /**
         * loads path from a PathPlanner formatted csv file.
         *
         * @param pathPlannerFile name of the file in the paths folder.
         */
        Path(String pathPlannerFile) {
            trajectory = CsvReader.read("/home/lvuser/paths/" + pathPlannerFile);
        }

        public Trajectory getTrajectory() {
            return trajectory;
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
