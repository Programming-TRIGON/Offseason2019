package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import frc.robot.command_group.DefenceMode;
import frc.robot.commands.EjectCargo;
import frc.robot.commands.MoveLiftWithJoystick;
import frc.robot.triggers.XboxTrigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  Button driverButtonY, driverButtonA, driverButtonB, driverButtonX, driverButtonLB, driverButtonRB;
  Button operatorButtonX, operatorButtonY, operatorButtonLB, operatorButtonRB, operatorButtonA, operatorButtonB, operatorStartButton, operatorButtonAxisRight, operatorButtonAxisLeft;
  POVButton operatorRightPOVButton, operatorLeftPOVButton, operatorBottomPOVButton, operatorTopPOVButton;
  XboxTrigger driverLTrigger, driverRTrigger, operatorRTrigger, operatorLTrigger;
  public XboxController operatorXbox, driverXbox;

  public OI(){
    
//------------------------------DRIVER--------------------------------------

    driverXbox = new XboxController(0);
    driverButtonA = new JoystickButton(driverXbox, 1);
    driverButtonB = new JoystickButton(driverXbox, 2);
    driverButtonX = new JoystickButton(driverXbox, 3);
    driverButtonY = new JoystickButton(driverXbox, 4);
    driverButtonLB = new JoystickButton(driverXbox, 5);
    driverButtonRB = new JoystickButton(driverXbox, 6);
    driverLTrigger = new XboxTrigger(this.driverXbox, Hand.kLeft);
    driverRTrigger = new XboxTrigger(this.driverXbox, Hand.kRight);

    driverRTrigger.whenActive(new EjectCargo());

//------------------------------OPERATER------------------------------------

    operatorXbox = new XboxController(1);
    operatorButtonA = new JoystickButton(operatorXbox, 1);
    operatorButtonB = new JoystickButton(operatorXbox, 2);
    operatorButtonX = new JoystickButton(operatorXbox, 3);
    operatorButtonY = new JoystickButton(operatorXbox, 4);
    operatorButtonLB = new JoystickButton(operatorXbox, 5);
    operatorButtonRB = new JoystickButton(operatorXbox, 6);
    operatorStartButton = new JoystickButton(operatorXbox, 8);
    operatorButtonAxisLeft = new JoystickButton(operatorXbox, 9);
    operatorButtonAxisRight = new JoystickButton(operatorXbox, 10);
    operatorRightPOVButton = new POVButton(operatorXbox, 90);
    operatorLeftPOVButton = new POVButton(operatorXbox, 270);
    operatorTopPOVButton = new POVButton(operatorXbox, 0);
    operatorBottomPOVButton = new POVButton(operatorXbox, 180);
    operatorLTrigger = new XboxTrigger(this.operatorXbox, Hand.kLeft);
    operatorRTrigger = new XboxTrigger(this.operatorXbox, Hand.kRight);

    operatorButtonAxisLeft.whenPressed(new MoveLiftWithJoystick(() -> -operatorXbox.getY(Hand.kLeft)));
    operatorStartButton.whenPressed(new DefenceMode());
  }
}
