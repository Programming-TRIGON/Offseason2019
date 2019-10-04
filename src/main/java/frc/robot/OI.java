package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  
  public XboxController driver = new XboxController(0);
  public Button MaxSpeedStop, DriveStraightWithGyro; 

  public OI(){

    MaxSpeedStop = new JoystickButton(driver, 3);
    DriveStraightWithGyro = new JoystickButton(driver, 2);
  }

}
