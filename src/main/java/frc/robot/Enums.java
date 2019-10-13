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

    private static double cargoOffset = RobotConstants.RobotDimensions.CARGO_LIFT_OFFSET;
    private static double hatchOffset = RobotConstants.RobotDimensions.HATCH_LIFT_OFFSET;
    public static enum LiftHeights {
        // TODO: add real values
        RocketCargoTop(212-cargoOffset), RocketCargoMiddle(141-cargoOffset), RocketCargoBottom(70-cargoOffset), RocketHatchTop(190-hatchOffset), RocketHatchMiddle(119-RobotConstants.RobotDimensions.HATCH_LIFT_OFFSET),
        HatchBottom(48-hatchOffset), CargoShip(100-cargoOffset), Floor(0), Feeder(48-hatchOffset); // Feeder is the same height as rocket hatch low.
        private double height;

        private LiftHeights(double height) {
            this.height = height;
        }

        public double getHeight() {
            return height;
        }
    }
}
