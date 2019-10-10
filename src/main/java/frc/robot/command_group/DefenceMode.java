package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import frc.robot.Robot;
import frc.robot.Enums.LiftHeights;
import frc.robot.commands.Commands;
import frc.robot.commands.EjectCargo;
import frc.robot.commands.setLiftHeight;

public class DefenceMode extends CommandGroup {
  /**
   * This command group makes sure the robot in inside the robots frame perimeter.
   */
  public DefenceMode() {
    addSequential(new ConditionalCommand(new EjectCargo(), new EjectHatch()){
    
      @Override
      protected boolean condition() {
        return Robot.cargoHolder.isCargoCollectedStall();
      }
    });
    addSequential(new setLiftHeight(LiftHeights.floor));
    addSequential(Commands.setTiltCommand(true));
  }
}
