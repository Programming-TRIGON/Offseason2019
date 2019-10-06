package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;

/** Hatch holder Subsystem catchs the hatch and eject it */
public class HatchHolder extends Subsystem {
  private DoubleSolenoid lockerSolenoid, ejectorSolenoid;
  private boolean lockState = true, ejectionState = false;

  public HatchHolder() {
    this.lockerSolenoid = RobotComponents.HatchHolder.LOCK_SOLENOID;
    this.ejectorSolenoid = RobotComponents.HatchHolder.EJECT_SOLENOID;
    
    // default values
    setLock(false);
    setEjection(false);
  }

  /**
   * sets the status of the front solenoid, the one that chatches the Hatch.
   */
  public void setLock(boolean lock) {
    if (ejectionState == false) {
      lockState = lock;
      this.lockerSolenoid.set(booleanToValue(lock));
    }
  }

  /**
   * sets the status of the rear solenoids, the ones that pushes the subsystem outwards.
   */
  public void setEjection(boolean eject) {
    if(!lockState) {
      ejectionState = eject;      
      this.ejectorSolenoid.set(booleanToValue(eject));
    }
  }

  public void setSolenoidsOff() {
    this.lockerSolenoid.set(Value.kOff);
    this.ejectorSolenoid.set(Value.kOff);
  }

  /** Convert boolean to DoubleSolenoid Value,
   *  true pushes the piston forward and false reverse the piston */
  private Value booleanToValue(boolean forward) {
    return forward ? Value.kForward : Value.kReverse;
  }

  @Override
  public void initDefaultCommand() {
  }
}
