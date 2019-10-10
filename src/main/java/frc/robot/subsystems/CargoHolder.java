package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;
import frc.robot.commands.KeepCargo;

/**
 * Contains the motor on the cargo folder to collect cargo,
 * and the cargo holder motor to hold cargo with the elevator.  
 */
public class CargoHolder extends Subsystem {
  private static final double DELTA_CURRENT = 3;
  private static final double CURRENT_OFFSET = -0.6384;
  private static final double CURRENT_FACTOR = 9.375;
  private DoubleSolenoid tiltSolenoid;
  private WPI_TalonSRX holderMotor;
  private boolean isCargoCollected;
  public CargoHolder() {
    this.tiltSolenoid = RobotComponents.CargoCollector.TILT_SOLENOID;
    this.holderMotor = RobotComponents.CargoCollector.HOLDER_MOTOR;

  }

  public void setHolderMotorPower(double power) {
   this.holderMotor.set(ControlMode.PercentOutput, power); 
  }

  public boolean isCargoCollected() {
    return isCargoCollected;
  }
  public void setIsCargoCollected(boolean isCollected){
    isCargoCollected = isCollected;
  }

  public boolean isCargoCollectedStall() {
    //TODO calibrate
    double actualCurrent = holderMotor.getOutputCurrent();
    double power = holderMotor.get();
    double expectedCurrent = getExpectedCurrent(power);
    return actualCurrent - expectedCurrent > DELTA_CURRENT;
  }

  private double getExpectedCurrent(double power) {
    return CURRENT_FACTOR * power + CURRENT_OFFSET;
  }

  public void setTilt(boolean tilt){
    this.tiltSolenoid.set(tilt ? Value.kForward : Value.kReverse);
  }

  public void setTiltSolenoidOff(){
    this.tiltSolenoid.set(Value.kOff);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new KeepCargo());
  }
}
