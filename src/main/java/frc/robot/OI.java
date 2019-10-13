package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import frc.robot.Enums.LiftHeights;
import frc.robot.commands.SetLiftHeight;
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

    public OI() {

//------------------------------DRIVER--------------------------------------

        this.driverXbox = new XboxController(0);
        this.driverButtonA = new JoystickButton(driverXbox, 1);
        this.driverButtonB = new JoystickButton(driverXbox, 2);
        this.driverButtonX = new JoystickButton(driverXbox, 3);
        this.driverButtonY = new JoystickButton(driverXbox, 4);
        this.driverButtonLB = new JoystickButton(driverXbox, 5);
        this.driverButtonRB = new JoystickButton(driverXbox, 6);
        this.driverLTrigger = new XboxTrigger(this.driverXbox, Hand.kLeft);
        this.driverRTrigger = new XboxTrigger(this.driverXbox, Hand.kRight);

//------------------------------OPERATER------------------------------------

        this.operatorXbox = new XboxController(1);
        this.operatorButtonA = new JoystickButton(operatorXbox, 1);
        this.operatorButtonB = new JoystickButton(operatorXbox, 2);
        this.operatorButtonX = new JoystickButton(operatorXbox, 3);
        this.operatorButtonY = new JoystickButton(operatorXbox, 4);
        this.operatorButtonLB = new JoystickButton(operatorXbox, 5);
        this.operatorButtonRB = new JoystickButton(operatorXbox, 6);
        this.operatorStartButton = new JoystickButton(operatorXbox, 8);
        this.operatorButtonAxisLeft = new JoystickButton(operatorXbox, 9);
        this.operatorButtonAxisRight = new JoystickButton(operatorXbox, 10);
        this.operatorRightPOVButton = new POVButton(operatorXbox, 90);
        this.operatorLeftPOVButton = new POVButton(operatorXbox, 270);
        this.operatorTopPOVButton = new POVButton(operatorXbox, 0);
        this.operatorBottomPOVButton = new POVButton(operatorXbox, 180);
        this.operatorLTrigger = new XboxTrigger(this.operatorXbox, Hand.kLeft);
        this.operatorRTrigger = new XboxTrigger(this.operatorXbox, Hand.kRight);

        operatorButtonLB.whenPressed(new ConditionalCommand(new SetLiftHeight(LiftHeights.HatchBottom),
                new SetLiftHeight(LiftHeights.RocketCargoBottom)) {
            @Override
            protected boolean condition() {
                return Robot.cargoHolder.isCargoCollected();
            }
        });
    }
}
