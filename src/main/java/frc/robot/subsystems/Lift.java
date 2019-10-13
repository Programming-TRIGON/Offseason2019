package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotComponents;
import frc.robot.RobotConstants;
import frc.robot.commands.MoveLiftWithJoystick;

/** This is the subsystem of the lift */
public class Lift extends Subsystem {
  private DigitalInput topSwitch, bottomSwitch;
  private WPI_TalonSRX frontMotor, rearMotor;
  private Encoder encoder;

  public Lift() {
    this.topSwitch = RobotComponents.Lift.LIFT_SWITCH_TOP;
    this.bottomSwitch = RobotComponents.Lift.LIFT_SWITCH_BOTTOM;
    // TODO: find what motor has the encoder
    this.frontMotor = RobotComponents.Lift.LIFT_MOTOR_FRONT;
    this.rearMotor = RobotComponents.Lift.LIFT_MOTOR_REAR;

    this.encoder = RobotComponents.Lift.encoder;
    this.frontMotor.setInverted(true);
    //this.frontMotor.set(ControlMode.Follower, this.rearMotor.getDeviceID());
    
    frontMotor.setNeutralMode(NeutralMode.Brake);
    rearMotor.setNeutralMode(NeutralMode.Brake);
    
    frontMotor.configOpenloopRamp(0.5);
    rearMotor.configOpenloopRamp(0.5);

    frontMotor.configContinuousCurrentLimit(6);
    rearMotor.configContinuousCurrentLimit(6);

    frontMotor.configPeakCurrentDuration(500);
    rearMotor.configPeakCurrentDuration(500);

    frontMotor.configPeakCurrentLimit(20);
    rearMotor.configPeakCurrentLimit(20);

    frontMotor.enableCurrentLimit(false);
    rearMotor.enableCurrentLimit(false);
  }

  public void setMotorsPower(double power) {
    this.rearMotor.set(ControlMode.PercentOutput, power);
    this.frontMotor.set(ControlMode.PercentOutput, power);
    System.out.println(frontMotor.getControlMode()); 
    System.out.println(rearMotor.getControlMode()); 
  }

  public boolean getTopSwitch() {
    return this.topSwitch.get();
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
    setDefaultCommand(new MoveLiftWithJoystick(Robot.oi.operatorXbox::getY)); 
  }
}
