package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;

/**
 * Contains the motor on the cargo folder to collect cargo,
 * and the cargo holder motor to hold cargo with the elevator.  
 */
public class CargoHolder extends Subsystem {
  private static final double DELTA_CURRENT = 3;
  private DoubleSolenoid tiltSolenoid;
  private WPI_TalonSRX holderMotor;
  private DigitalInput holderSwitch;

  public CargoHolder() {
    this.tiltSolenoid = RobotComponents.CargoCollector.TILT_SOLENOID;
    this.holderMotor = RobotComponents.CargoCollector.HOLDER_MOTOR;
    this.holderSwitch = RobotComponents.CargoCollector.HOLDER_SWITCH;
  }

  public void setHolderMotorPower(double power) {
   this.holderMotor.set(ControlMode.PercentOutput, power); 
  }

  public boolean isCargoCollected() {
    return this.holderSwitch.get();
  }

  public boolean isCargoCollectedStall() {
    //TODO calibrate
    double actualCurrent = holderMotor.getOutputCurrent();
    double power = 0.4;
    double expectedCurrent = 9.375 * power - 0.6384;
    return actualCurrent - expectedCurrent > DELTA_CURRENT;
  }

  public void setTilt(boolean tilt){
    this.tiltSolenoid.set(tilt ? Value.kForward : Value.kReverse);
  }

  public void setTiltSolenoidOff(){
    this.tiltSolenoid.set(Value.kOff);
  }

  @Override
  public void initDefaultCommand() {
  }
}
