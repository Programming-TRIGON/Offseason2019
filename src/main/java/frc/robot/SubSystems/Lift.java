package frc.robot.SubSystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;
import frc.robot.RobotConstants;

/** This is the subsystem of the lift */
public class Lift extends Subsystem {
  private SpeedControllerGroup motors;
  private DigitalInput topSwitch, bottomSwitch;
  private WPI_TalonSRX encoder;

  public Lift() {
    this.motors = RobotComponents.Lift.LIFT_MOTORS;
    this.topSwitch = RobotComponents.Lift.LIFT_SWITCH_TOP;
    this.bottomSwitch = RobotComponents.Lift.LIFT_SWITCH_BOTTOM;
    // TODO: find what motor has the encoder
    this.encoder = RobotComponents.Lift.LIFT_MOTOR_FRONT;
  }

  public void setMotorsPower(double power) {
    this.motors.set(power);
  }

  public boolean getTopSwitch() {
    return this.topSwitch.get();
  }

  public boolean getBottomSwitch() {
    return this.bottomSwitch.get();
  }

  public int getHeight() {
    return this.encoder.getSelectedSensorPosition() / RobotConstants.Sensors.LIFT_ENCODER_TPD
        + RobotConstants.Sensors.LIFT_ENCODER_OFFSET;
  }

  public void restEncoderHeight() {
    this.encoder.setSelectedSensorPosition(0);
  }

  @Override
  public void initDefaultCommand() {
  }
}
