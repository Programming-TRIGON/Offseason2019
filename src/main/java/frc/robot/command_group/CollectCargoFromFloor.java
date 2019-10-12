package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Enums.LiftHeights;
import frc.robot.Robot;
import frc.robot.commands.CollectCargo;
import frc.robot.commands.SetLiftHeight;
import frc.robot.commands.Commands;
import frc.robot.commands.WaitUntil;


public class CollectCargoFromFloor extends CommandGroup {
  /**
   * This CG lowers the lift then collects the cargo then highers the lift to the lowest scoring height.
   */
  public CollectCargoFromFloor() {
    addSequential(Commands.setTiltCommand(true));
    SetLiftHeight setLiftHeight = new SetLiftHeight(LiftHeights.Floor);
    addParallel(setLiftHeight);
    addSequential(new WaitUntil(setLiftHeight::isOnTarget));
    addSequential(new CollectCargo());
    addSequential(new SetLiftHeight(LiftHeights.RocketCargoBottom));
  }
}