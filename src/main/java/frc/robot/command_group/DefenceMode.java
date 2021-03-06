package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import frc.robot.Robot;
import frc.robot.Enums.LiftHeights;
import frc.robot.commands.EjectCargo;
import frc.robot.commands.SetLiftHeight;
import frc.robot.commands.WaitUntil;

public class DefenceMode extends CommandGroup {
  /**
   * This command group makes sure the robot in inside the robots frame perimeter.
   */
  public DefenceMode() {
    addSequential(new ConditionalCommand(new EjectCargo(), new EjectHatch())
    {
      @Override
      protected boolean condition() {
        return true; //Robot.cargoHolder.isCargoCollected(); //Change the condition to this when we have hatch subsystem
      }
    });
    SetLiftHeight setLiftHeight = new SetLiftHeight(LiftHeights.Floor);
    addParallel(setLiftHeight);
    addSequential(new WaitUntil(setLiftHeight::isOnTarget)); 
  }
}
