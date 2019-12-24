package frc.robot.utils;

import frc.robot.Enums.Target;
import frc.robot.Robot;

/**
 * this class calculates the position of the robot from a target using vision and gyro
 */
public class VisionLocator {
  private Target target;

  public VisionLocator(Target target) {
    this.target = target;
  }

  public double calculateXDisplacement() {
    //calculations are from CAV conference.
    if (Robot.limelight.getTv()) {
      double betaAngle = getBeta();
      return Robot.limelight.getDistance() * Math.sin(betaAngle);
    }
    return 0;
  }

  public double getBeta() {
    //calculations are from CAV conference.
    if (Robot.limelight.getTv()) {
      return Robot.drivetrain.getAngle() - target.getAbsoluteAngle() + Robot.limelight.getTx();
    }
    return 0;
  }
}
