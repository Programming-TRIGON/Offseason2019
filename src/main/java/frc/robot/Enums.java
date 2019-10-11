package frc.robot;

/**
 * This class contains enums for the robot, we convert human readable language
 * to the robot language
 */
public class Enums {
    public enum Target {
        // TODO: set real values
        RocketMiddle(0), RocketSide(1), Feeder(2), CargoShip(3);
    
        private final int index;
    
        Target(int index) {
            this.index = index;

        }
    
        public int getIndex() {
            return index;
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
