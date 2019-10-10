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
        SHIP("ship.csv"), PATH2("path2.csv"),
        PATH2_JACI(new Waypoint[] { new Waypoint(0, 0, 0),
                new Waypoint(0, RobotConstants.FieldDimensions.PATH2_DISTANCE, 0) }),
        PATH3("path3.csv"), PATH3_JACI(new Waypoint[] { new Waypoint(0, 0, 0),
                new Waypoint(0, RobotConstants.FieldDimensions.PATH3_DISTANCE, 0) });

        private final Trajectory trajectory;

        private Path(Waypoint[] path) {
            trajectory = Pathfinder.generate(path,
                    new Trajectory.Config(Trajectory.FitMethod.HERMITE_QUINTIC, Trajectory.Config.SAMPLES_HIGH,
                            RobotConstants.MotionProfiling.TIMEFRAME, RobotConstants.MotionProfiling.MAX_VELOCITY,
                            RobotConstants.MotionProfiling.MAX_ACCELERATION, RobotConstants.MotionProfiling.MAX_JERK));
        }

        private Path(File csvFile) {
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
        // TODO: set real values
        RocketMiddle(0, 91, 25), RocketSide(1, 0, 0), Feeder(2, 0, 0), CargoShip(3, 0, 0);

        private final int index;
        private final double setpointX, setpointY;

        Target(int index, double setpointX, double setpointY) {
            this.index = index;
            this.setpointX = setpointX;
            this.setpointY = setpointY;
        }

        public int getIndex() {
            return index;
        }

        public double getSetpointX() {
            return setpointX;
        }

        public double getSetpointY() {
            return setpointY;
        }
    }

    public static enum LiftHeights {
        // TODO: add real values
        RocketCargoTop(0), RocketCargoMiddle(0), RocketCargoBottom(0), RocketHatchTop(0), RocketHatchMiddle(0),
        HatchBottom(0), CargoShip(0);
        private double height;

        private LiftHeights(double height) {
            this.height = height;
        }

        public double getHeight() {
            return height;
        }
    }
}
