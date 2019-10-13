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
        RocketCargoTop(212-RobotConstants.RobotDimensions.CARGO_LIFT_OFFSET), RocketCargoMiddle(141-RobotConstants.RobotDimensions.CARGO_LIFT_OFFSET), RocketCargoBottom(70-RobotConstants.RobotDimensions.CARGO_LIFT_OFFSET), RocketHatchTop(190-RobotConstants.RobotDimensions.HATCH_LIFT_OFFSET), RocketHatchMiddle(119-RobotConstants.RobotDimensions.HATCH_LIFT_OFFSET),
        HatchBottom(48-RobotConstants.RobotDimensions.HATCH_LIFT_OFFSET), CargoShip(100-RobotConstants.RobotDimensions.CARGO_LIFT_OFFSET), Floor(0), Feeder(48-RobotConstants.RobotDimensions.HATCH_LIFT_OFFSET); // Feeder is the same height as rocket hatch low.
        private double height;

        private LiftHeights(double height) {
            this.height = height;
        }

        public double getHeight() {
            return height;
        }
    }
}
