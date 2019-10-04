package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;

/** Hatch holder Subsystem catchs the hatch and eject it */
public class HatchHolder extends Subsystem {
  private DoubleSolenoid lockerSolenoid, ejectorSolenoid;
  private Value lockState = Value.kForward, ejectionState = Value.kReverse;
  private boolean lockStateB = true, ejectionStateB = false;

  public HatchHolder() {
    this.lockerSolenoid = RobotComponents.HatchHolder.LOCK_SOLENOID;
    this.ejectorSolenoid = RobotComponents.HatchHolder.EJECT_SOLENOID;
    
    // default values
    setLock(Value.kReverse);
    setEjection(Value.kReverse);
  }

  /**
   * sets the status of the front solenoid, the one that chatches the Hatch.
   */
  public void setLock(Value value) {
    if (ejectionState == Value.kReverse) {
      lockState = value;
      this.lockerSolenoid.set(value);
    }
  }

  public void setLock(boolean lock) {
    if (ejectionStateB == false) {
      lockStateB = lock;
      this.lockerSolenoid.set(Value.kForward);
    }
  }

  /**
   * sets the status of the rear solenoids, the ones that pushes the subsystem outwards.
   */
  public void setEjection(Value value) {
    if(lockState == Value.kReverse){
      ejectionState = value;      
      this.ejectorSolenoid.set(value);
    }
  }

  public void setSolenoidsOff() {
    this.lockerSolenoid.set(Value.kOff);
    this.ejectorSolenoid.set(Value.kOff);
  }

  private Value booleanToValue(boolean bool){
    return bool ? Value.kForward : Value.kReverse;
  }

  @Override
  public void initDefaultCommand() {
  }
}
