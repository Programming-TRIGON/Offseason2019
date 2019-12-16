package frc.robot.utils;

import frc.robot.Enums.Target;
import frc.robot.Robot;

/**
 * this class calculates the position of the robot from a target using vision and gyro
 */
public class VisionLocator {
  private final Target target;

  public VisionLocator(Target target) {
    this.target = target;
  }

  public double calculateXDisplacement() {
    //calculations are from CAV conference.
    double betaAngle = Robot.drivetrain.getAngle() - target.getAbsoluteAngle() - 180 + Robot.limelight.getTx();
    return Robot.limelight.getDistance() * Math.sin(betaAngle);
  }
}
