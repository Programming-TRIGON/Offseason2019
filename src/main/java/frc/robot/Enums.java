package frc.robot;

/**
 * This class contains enums for the robot, we convert human readable language
 * to the robot language
 */
public class Enums {
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
