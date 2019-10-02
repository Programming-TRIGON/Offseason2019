package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.DriveArcade;;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  
  public Joystick driver = new Joystick(0);

  public OI(){
    new DriveArcade(() -> this.driver.getX(), () -> this.driver.getY());
  }
}
