package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;
import frc.robot.RobotConstants;

/** This is the subsystem of the lift */
public class Lift extends Subsystem {
  private DigitalInput bottomSwitch;
  private WPI_TalonSRX leftMotor, rightMotor;
  private Encoder encoder;
  private boolean isOverride;

  public Lift() {
    this.bottomSwitch = RobotComponents.Lift.LIFT_SWITCH_BOTTOM;
    
    this.leftMotor = RobotComponents.Lift.LIFT_MOTOR_LEFT;
    this.rightMotor = RobotComponents.Lift.LIFT_MOTOR_RIGHT;

    this.encoder = RobotComponents.Lift.encoder;
    
    this.leftMotor.setInverted(true);
    
    leftMotor.setNeutralMode(NeutralMode.Brake);
    rightMotor.setNeutralMode(NeutralMode.Brake);
    
    leftMotor.configOpenloopRamp(0.5);
    rightMotor.configOpenloopRamp(0.5);

    leftMotor.configContinuousCurrentLimit(6);
    rightMotor.configContinuousCurrentLimit(6);

    leftMotor.configPeakCurrentDuration(500);
    rightMotor.configPeakCurrentDuration(500);

    leftMotor.configPeakCurrentLimit(20);
    rightMotor.configPeakCurrentLimit(20);

    leftMotor.enableCurrentLimit(false);
    rightMotor.enableCurrentLimit(false);
  }

  public void setMotorsPower(double power) {
    this.rightMotor.set(ControlMode.PercentOutput, power);
    this.leftMotor.set(ControlMode.PercentOutput, power);
  }

  public boolean getBottomSwitch() {
    return this.bottomSwitch.get();
  }

  public double getHeight() {
    return this.encoder.get() / RobotConstants.Sensors.LIFT_ENCODER_DISTANCE_PER_TICKS
        + RobotConstants.Sensors.LIFT_ENCODER_OFFSET;
  }

  public void resetEncoderHeight() {
    this.encoder.reset();
  }

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new MoveLiftWithJoystick(Robot.oi.operatorXbox::getY)); //For emergency!
  }

  public boolean getIsOverride() {
    return isOverride;
  }

  public void setIsOverride(boolean isOverride) {
    this.isOverride = isOverride;
  }
}
