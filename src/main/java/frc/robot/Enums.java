package frc.robot;

/**
 * This class contains enums for the robot, we convert human readable language
 * to the robot language
 */
public class Enums {
    public static enum LiftHeights {
        // TODO: add real values and put real enums
        RocketTop(0), RocketMiddle(0), RocketBottom(0);
        private double height;

        private LiftHeights(double height) {
            this.height = height;
        }

        public double getHeight() {
            return this.height;
        }
    }
}
