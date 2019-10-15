package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Enums.LiftHeights;
import frc.robot.Enums.ScoreHeight;
import frc.robot.command_group.CollectCargoFromFloor;
import frc.robot.command_group.CollectHatchFromFeeder;
import frc.robot.command_group.DefenceMode;
import frc.robot.command_group.PrepareToScore;
import frc.robot.commands.Commands;
import frc.robot.commands.EjectCargo;
import frc.robot.commands.HatchHolderLock;
import frc.robot.commands.MoveLiftWithJoystick;
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

public OI(){
    
//------------------------------DRIVER--------------------------------------

    driverXbox = new XboxController(0);
    driverButtonA = new JoystickButton(driverXbox, 1);
    driverButtonB = new JoystickButton(driverXbox, 2);
    driverButtonX = new JoystickButton(driverXbox, 3);
    driverButtonY = new JoystickButton(driverXbox, 4);
    driverButtonLB = new JoystickButton(driverXbox, 5);
    driverButtonRB = new JoystickButton(driverXbox, 6);
    driverLTrigger = new XboxTrigger(driverXbox, Hand.kLeft);
    driverRTrigger = new XboxTrigger(driverXbox, Hand.kRight);

    driverRTrigger.whenActive(new EjectCargo());
    driverLTrigger.whenActive(new HatchHolderLock(false));
    driverLTrigger.whenInactive(new HatchHolderLock(true));

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
    operatorLTrigger = new XboxTrigger(operatorXbox, Hand.kLeft);
    operatorRTrigger = new XboxTrigger(operatorXbox, Hand.kRight);

    CommandGroup collectCargoFromFloor = new CollectCargoFromFloor();
    operatorButtonA.whenPressed(collectCargoFromFloor);
    operatorButtonA.whenReleased(Commands.cancelCommand(collectCargoFromFloor));
    
    operatorButtonB.whenPressed(new CollectHatchFromFeeder()); // move null from double solenoid on robot Components
    operatorButtonB.whenReleased(new HatchHolderLock(true));

    operatorButtonAxisLeft.whenPressed(new MoveLiftWithJoystick(() -> -operatorXbox.getY(Hand.kLeft))); //ISSUE - not work with other command groups (collectCargoFromFloor, etc.)
    operatorStartButton.whenPressed(new DefenceMode());

    operatorButtonLB.whenPressed(new PrepareToScore(ScoreHeight.kLow));        
    operatorButtonRB.whenPressed(new PrepareToScore(ScoreHeight.kMedium));
    //operatorRTrigger.whenActive(new PrepareToScore(ScoreHeight.kHigh));

    operatorButtonX.whenPressed(new SetLiftHeight(LiftHeights.CargoShip));
  }

  public OI(boolean isHillel) {
    yoavSettings();
    if(isHillel){
      hillelSettings();
    } else {
      grossmanSettings();
    }
  }

  private void yoavSettings() {
    driverXbox = new XboxController(0);
    driverButtonA = new JoystickButton(driverXbox, 1);
    driverButtonB = new JoystickButton(driverXbox, 2);
    driverButtonX = new JoystickButton(driverXbox, 3);
    driverButtonY = new JoystickButton(driverXbox, 4);
    driverButtonLB = new JoystickButton(driverXbox, 5);
    driverButtonRB = new JoystickButton(driverXbox, 6);
    driverLTrigger = new XboxTrigger(driverXbox, Hand.kLeft);
    driverRTrigger = new XboxTrigger(driverXbox, Hand.kRight);

    driverRTrigger.whenActive(new EjectCargo());
    driverLTrigger.whenActive(new HatchHolderLock(false));
    driverLTrigger.whenInactive(new HatchHolderLock(true));
  }

  private void hillelSettings() {
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
    operatorLTrigger = new XboxTrigger(operatorXbox, Hand.kLeft);
    operatorRTrigger = new XboxTrigger(operatorXbox, Hand.kRight);

    CommandGroup collectCargoFromFloor = new CollectCargoFromFloor();
    operatorButtonA.whenPressed(collectCargoFromFloor);
    operatorButtonA.whenReleased(Commands.cancelCommand(collectCargoFromFloor));
    
    operatorButtonB.whenPressed(new CollectHatchFromFeeder()); // move null from double solenoid on robot Components
    operatorButtonB.whenReleased(new HatchHolderLock(true));

    operatorButtonAxisLeft.whenPressed(new MoveLiftWithJoystick(() -> -operatorXbox.getY(Hand.kLeft))); //ISSUE - not work with other command groups (collectCargoFromFloor, etc.)
    operatorStartButton.whenPressed(new DefenceMode());

    operatorButtonLB.whenPressed(new PrepareToScore(ScoreHeight.kLow));        
    operatorButtonRB.whenPressed(new PrepareToScore(ScoreHeight.kMedium));

    operatorButtonX.whenPressed(new SetLiftHeight(LiftHeights.CargoShip));    
  }

  public void grossmanSettings() {
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
    operatorLTrigger = new XboxTrigger(operatorXbox, Hand.kLeft);
    operatorRTrigger = new XboxTrigger(operatorXbox, Hand.kRight);

    CommandGroup collectCargoFromFloor = new CollectCargoFromFloor();
    operatorButtonA.whenPressed(collectCargoFromFloor);
    operatorButtonA.whenReleased(Commands.cancelCommand(collectCargoFromFloor));
    
    operatorButtonB.whenPressed(new CollectHatchFromFeeder()); // move null from double solenoid on robot Components
    operatorButtonB.whenReleased(new HatchHolderLock(true));

    operatorButtonAxisLeft.whenPressed(new MoveLiftWithJoystick(() -> -operatorXbox.getY(Hand.kLeft))); //ISSUE - not work with other command groups (collectCargoFromFloor, etc.)
    operatorStartButton.whenPressed(new DefenceMode());

    operatorRightPOVButton.whenPressed(new SetLiftHeight(LiftHeights.CargoShip));
    operatorBottomPOVButton.whenPressed(new PrepareToScore(ScoreHeight.kLow));
    operatorTopPOVButton.whenPressed(new  PrepareToScore(ScoreHeight.kMediumGrossman)); 
  }
}
