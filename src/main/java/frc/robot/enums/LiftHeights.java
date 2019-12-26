package frc.robot.enums;

import frc.robot.RobotConstants.RobotDimensions;

/**
 * Lift height enum and height affilation
 */
public enum LiftHeights {
    RocketCargoTop(212-RobotDimensions.CARGO_LIFT_OFFSET), 
    RocketCargoMiddle(141-RobotDimensions.CARGO_LIFT_OFFSET), 
    RocketCargoBottom(70-RobotDimensions.CARGO_LIFT_OFFSET), 
    RocketHatchTop(190-RobotDimensions.HATCH_LIFT_OFFSET), 
    RocketHatchMiddle(119-RobotDimensions.HATCH_LIFT_OFFSET),
    HatchBottom(48-RobotDimensions.HATCH_LIFT_OFFSET), 
    CargoShip(100-RobotDimensions.CARGO_LIFT_OFFSET), 
    Floor(0), 
    Feeder(48-RobotDimensions.HATCH_LIFT_OFFSET); // Feeder is the same height as rocket hatch low.
    private double height;

    LiftHeights(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }
}