package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;

/** Folds and unfolds the cargo collector */
public class CargoFolder extends Subsystem {
  private DoubleSolenoid folderSolenoid;
  
  public CargoFolder() {
    this.folderSolenoid = RobotComponents.CargoFolder.FOLDER_SOLENOID;
    setFold(false);
  }

  /** folds/unfolds the Subsystem */
  public void setFold(boolean fold) {
    if(fold) 
      this.folderSolenoid.set(Value.kForward); 
    else 
      this.folderSolenoid.set(Value.kReverse);
  }

  public boolean isFold(){
    return this.folderSolenoid.get().equals(Value.kForward);
  }

  public void setSolenoidOff(){
    this.folderSolenoid.set(Value.kOff);
  }

  @Override
  public void initDefaultCommand() {
  }
}