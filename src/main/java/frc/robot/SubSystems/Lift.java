package frc.robot.SubSystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;
import frc.robot.RobotConstants;

/** This is the subsystem of the lift */
public class Lift extends Subsystem {
  private DigitalInput topSwitch, bottomSwitch;
  private TalonSRX frontMotor, rearMotor;

  public Lift() {
    this.topSwitch = RobotComponents.Lift.LIFT_SWITCH_TOP;
    this.bottomSwitch = RobotComponents.Lift.LIFT_SWITCH_BOTTOM;
    // TODO: find what motor has the encoder
    this.frontMotor = RobotComponents.Lift.LIFT_MOTOR_FRONT;
    this.rearMotor = RobotComponents.Lift.LIFT_MOTOR_REAR;
    this.rearMotor.follow(this.frontMotor);
  }

  public void setMotorsPower(double power) {
    this.frontMotor.set(ControlMode.PercentOutput, power);
  }

  public boolean getTopSwitch() {
    return this.topSwitch.get();
  }

  public boolean getBottomSwitch() {
    return this.bottomSwitch.get();
  }

  public int getHeight() {
    return this.frontMotor.getSelectedSensorPosition() / RobotConstants.Sensors.LIFT_ENCODER_TPD
        + RobotConstants.Sensors.LIFT_ENCODER_OFFSET;
  }

  public void resetEncoderHeight() {
    this.frontMotor.setSelectedSensorPosition(0);
  }

  @Override
  public void initDefaultCommand() {
  }
}
