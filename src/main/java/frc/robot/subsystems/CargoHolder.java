package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;

/**
 * Contains the motor on the cargo folder to collect cargo,
 * and the cargo holder motor to hold cargo with the elevator.  
 */
public class CargoHolder extends Subsystem {
  private static final double DELTA_CURRENT = 6;
  private static final double CURRENT_OFFSET = 0.4821;
  private static final double CURRENT_FACTOR = 5;
  private WPI_TalonSRX holderMotor;
  private boolean isCargoCollected;
  public CargoHolder() {
    this.holderMotor = RobotComponents.CargoCollector.HOLDER_MOTOR;
    this.holderMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void setHolderMotorPower(double power) {
   this.holderMotor.set(ControlMode.PercentOutput, -power);
  }

  public boolean isCargoCollected() {
    return isCargoCollected;
  }
  public void setIsCargoCollected(boolean isCollected){
    isCargoCollected = isCollected;
  }

  public boolean isCargoCollectedStall() {
    return getActualCurrent() > 10;
  }

  public double getActualCurrent() {
    return holderMotor.getOutputCurrent();
  }

  public double getExpectedCurrent() {
    return CURRENT_FACTOR * holderMotor.get() + CURRENT_OFFSET;
  }

  @Override
  public void initDefaultCommand() {
  }
}
