package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Enums.LiftHeights;
import frc.robot.Enums.ScoreHeight;
import frc.robot.command_group.CollectCargoFromFloor;
import frc.robot.command_group.CollectHatchFromFeeder;
import frc.robot.command_group.DefenceMode;
import frc.robot.command_group.PrepareToScore;
import frc.robot.command_group.PutHatch;
import frc.robot.commands.CollectCargo;
import frc.robot.commands.Commands;
import frc.robot.commands.DriveArcade;
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
  Button driverButtonY, driverButtonA, driverButtonB, driverButtonX, driverButtonLB, driverButtonRB, driverStartButton;
  Button operatorButtonX, operatorButtonY, operatorButtonLB, operatorButtonRB, operatorButtonA, operatorButtonB, operatorStartButton, operatorButtonAxisRight, operatorButtonAxisLeft;
  POVButton operatorRightPOVButton, operatorLeftPOVButton, operatorBottomPOVButton, operatorTopPOVButton;
  POVButton driverRightPOVButton, driverLeftPOVButton, driverBottomPOVButton, driverTopPOVButton;
  XboxTrigger driverLTrigger, driverRTrigger, operatorRTrigger, operatorLTrigger;
  public XboxController operatorXbox, driverXbox;

  public OI() {
    CameraServer.getInstance().startAutomaticCapture(0);
    setButtons();
    yoavSettings();
    hillelSettings();
  }

  public OI(boolean isHillel) {
    CameraServer.getInstance().startAutomaticCapture(0);
    setButtons();
    yoavSettings();
    /*if(isHillel){
      hillelSettings();
    } else {
      grossmanSettings();
    }*/
  }

  private void setButtons() {
    // --------------- Driver -----------------------------
    
    driverXbox = new XboxController(0);
    driverButtonA = new JoystickButton(driverXbox, 1);
    driverButtonB = new JoystickButton(driverXbox, 2);
    driverButtonX = new JoystickButton(driverXbox, 3);
    driverButtonY = new JoystickButton(driverXbox, 4);
    driverButtonLB = new JoystickButton(driverXbox, 5);
    driverButtonRB = new JoystickButton(driverXbox, 6);
    driverStartButton = new JoystickButton(driverXbox, 8);
    driverLTrigger = new XboxTrigger(driverXbox, Hand.kLeft);
    driverRTrigger = new XboxTrigger(driverXbox, Hand.kRight);
    driverRightPOVButton = new POVButton(driverXbox, 90);
    driverLeftPOVButton = new POVButton(driverXbox, 270);
    driverTopPOVButton = new POVButton(driverXbox, 0);
    driverBottomPOVButton = new POVButton(driverXbox, 180);

    // --------------- Operator ---------------------------

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
  }

  private void yoavSettings() {
    driverButtonX.whenPressed(new DriveArcade(() -> Robot.oi.driverXbox.getX(Hand.kLeft), () -> Robot.oi.driverXbox.getY(Hand.kLeft)));
    driverButtonY.whenPressed(new DriveArcade(() -> Robot.oi.driverXbox.getX(Hand.kLeft), () -> Robot.oi.driverXbox.getTriggerAxis(Hand.kLeft), () -> Robot.oi.driverXbox.getTriggerAxis(Hand.kRight)));
    driverStartButton.whenPressed(Commands.toggleDrive());
    driverButtonLB.whenActive(new EjectCargo());
    driverButtonRB.whenActive(new HatchHolderLock(false));
    driverButtonRB.whenInactive(new HatchHolderLock(true));
    Command c = new PutHatch(true);
    driverButtonB.whenPressed(c);
    driverButtonB.whenReleased(Commands.cancelCommand(c));
  }

  private void hillelSettings() {
    CommandGroup collectCargoFromFloor = new CollectCargoFromFloor();
    operatorButtonA.whenPressed(collectCargoFromFloor);
    operatorButtonA.whenReleased(Commands.cancelCommand(collectCargoFromFloor));
    
    CommandGroup collectHatchFromFeeder = new CollectHatchFromFeeder();
    operatorButtonB.whenPressed(collectHatchFromFeeder);
    operatorButtonB.whenReleased(Commands.cancelCommand(collectHatchFromFeeder));

    operatorButtonAxisLeft.whenPressed(new MoveLiftWithJoystick(() -> -operatorXbox.getY(Hand.kLeft))); //ISSUE - not work with other command groups (collectCargoFromFloor, etc.)
    operatorStartButton.whenPressed(new DefenceMode());

    operatorButtonLB.whenPressed(new PrepareToScore(ScoreHeight.kLow));        
    operatorButtonRB.whenPressed(new PrepareToScore(ScoreHeight.kMedium));

    operatorButtonX.whenPressed(new CollectCargo());
    operatorButtonY.whenPressed(new SetLiftHeight(LiftHeights.CargoShip));
  }

  public void grossmanSettings() {
    CommandGroup collectCargoFromFloor = new CollectCargoFromFloor();
    operatorButtonA.whenPressed(collectCargoFromFloor);
    operatorButtonA.whenReleased(Commands.cancelCommand(collectCargoFromFloor));
    
    operatorButtonB.whenPressed(new CollectHatchFromFeeder());

    operatorButtonAxisLeft.whenPressed(new MoveLiftWithJoystick(() -> -operatorXbox.getY(Hand.kLeft))); //ISSUE - not work with other command groups (collectCargoFromFloor, etc.)
    operatorStartButton.whenPressed(new DefenceMode());

    operatorRightPOVButton.whenPressed(new SetLiftHeight(LiftHeights.CargoShip));
    operatorBottomPOVButton.whenPressed(new PrepareToScore(ScoreHeight.kLow));
    operatorTopPOVButton.whenPressed(new  PrepareToScore(ScoreHeight.kMediumGrossman)); 
  }
}
