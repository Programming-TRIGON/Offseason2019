package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  
  public XboxController driver = new XboxController(0);

  public OI(){
  }
  public double getJoystickDirection(){
    return Math.atan2(this.driver.getX(), -this.driver.getY());
  }
}
