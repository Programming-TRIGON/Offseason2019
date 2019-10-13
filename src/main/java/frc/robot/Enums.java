package frc.robot;

/**
 * This class contains enums for the robot, we convert human readable language
 * to the robot language
 */
public class Enums {
    public enum Target {
        // TODO: set real values
        RocketMiddle(1), RocketSide(0), Feeder(0), CargoShip(0);
    
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
        RocketCargoTop(212), RocketCargoMiddle(141), RocketCargoBottom(70), RocketHatchTop(190), RocketHatchMiddle(119),
        HatchBottom(48), CargoShip(100), Floor(0), Feeder(48); // Feeder is the same height as rocket hatch low.
        private double height;

        private LiftHeights(double height) {
            this.height = height;
        }

        public double getHeight() {
            return height;
        }
    }
}
