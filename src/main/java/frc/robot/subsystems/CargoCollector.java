package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;

/**
 * Contains the motor on the cargo folder to collect cargo,
 * and the cargo holder motor to hold cargo with the elevator.  
 */
public class CargoCollector extends Subsystem {
  private WPI_TalonSRX collectorMotor, holderMotor;
  private DigitalInput holderSwitch;

  public CargoCollector() {
    this.collectorMotor = RobotComponents.CargoCollector.COLLECTOR_MOTOR;
    this.holderMotor = RobotComponents.CargoCollector.HOLDER_MOTOR;
    this.holderSwitch = RobotComponents.CargoCollector.HOLDER_SWITCH;
  }

  public void setHolderMotorPower(double power) {
   this.holderMotor.set(ControlMode.PercentOutput, power); 
  }

  public void setCollectorMotorPower(double power) {
    this.collectorMotor.set(ControlMode.PercentOutput, power); 
  }

  public void setMotorsPower(double power) {
    this.holderMotor.set(ControlMode.PercentOutput, power); 
    this.collectorMotor.set(ControlMode.PercentOutput, power); 
  }

  public boolean isCargoCollected() {
    return this.holderSwitch.get();
  }

  @Override
  public void initDefaultCommand() {
  }
}
