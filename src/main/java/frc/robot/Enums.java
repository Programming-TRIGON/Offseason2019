package frc.robot;

import java.io.IOException;
import java.io.File;

import frc.robot.utils.CsvReader;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

/**
 * This class contains enums for the robot,
 * we convert human readable language to the robot language 
 */
public class Enums {
    public enum Path {
        ROCKET(new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(2, -3, 0) }),
        SHIP("ship.csv"),
        PATH2("path2.csv"),
        PATH3("path3.csv"),
        TEST_JACI(new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(1, -1, -90) });
    
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
    
}
