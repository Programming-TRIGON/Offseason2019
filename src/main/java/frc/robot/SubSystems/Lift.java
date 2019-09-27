package frc.robot.SubSystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;
import frc.robot.RobotConstants;

/** This is the subsystem of the lift */
public class Lift extends Subsystem {
  private SpeedControllerGroup motors;
  private Encoder encoder;
  private DigitalInput switchTop, switchBottom;

  public Lift() {
    this.motors = RobotComponents.Lift.LIFT_MOTORS;
    this.encoder = RobotComponents.Lift.LIFT_ENCODER;
    this.switchTop = RobotComponents.Lift.LIFT_SWITCH_TOP;
    this.switchBottom = RobotComponents.Lift.LIFT_SWITCH_BOTTOM;
    this.encoder.setDistancePerPulse(RobotConstants.Sensors.LIFT_ENCODER_DPP);
  }

  public void setMotorsPower(double power) {
    this.motors.set(power);
  }

  public double getEncoder() {
    return this.encoder.getDistance();
  }

  public void resetEncoder() {
    this.encoder.reset();
  }

  public boolean getTopSwitch() {
    return this.switchTop.get();
  }

  public boolean getBottomSwitch() {
    return this.switchBottom.get();
  }

  @Override
  public void initDefaultCommand() {
  }
}
