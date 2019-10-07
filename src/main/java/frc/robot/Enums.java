package frc.robot;

/**
 * This class contains enums for the robot, we convert human readable language
 * to the robot language
 */
public class Enums {
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
        HatchBottom(0), CargoShip(0),Feeder(0);
        private double height;

        private LiftHeights(double height) {
            this.height = height;
        }

        public double getHeight() {
            return height;
        }
    }
}
