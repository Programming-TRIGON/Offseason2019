package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;

/** Hatch holder Subsystem catchs the hatch and eject it */
public class HatchHolder extends Subsystem {
  private DoubleSolenoid lockerSolenoid;

  public HatchHolder() {
    this.lockerSolenoid = RobotComponents.HatchHolder.LOCK_SOLENOID;

    // default values TODO uncomment
    setLock(true);
  }

  /**
   * sets the status of the front solenoid, the one that chatches the Hatch.
   */
  public void setLock(boolean lock) {
    this.lockerSolenoid.set(booleanToValue(lock));
  }

  public void setSolenoidsOff() {
    this.lockerSolenoid.set(Value.kOff);
  }

  public boolean isLock() {
    return lockerSolenoid.get().equals(Value.kForward);
  }

  /**
   * Convert boolean to DoubleSolenoid Value, true pushes the piston forward and
   * false reverse the piston
   */
  private Value booleanToValue(boolean forward) {
    return forward ? Value.kForward : Value.kReverse;
  }

  @Override
  public void initDefaultCommand() {
  }
}
