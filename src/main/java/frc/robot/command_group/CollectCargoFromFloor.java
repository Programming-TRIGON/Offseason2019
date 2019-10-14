package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Enums.LiftHeights;
import frc.robot.commands.CollectCargo;
import frc.robot.commands.SetLiftHeight;

public class CollectCargoFromFloor extends CommandGroup {
  /**
   * This CG lowers the lift then collects the cargo then highers the lift to the lowest scoring height.
   */
  public CollectCargoFromFloor() {
    addSequential(new SetLiftHeight(LiftHeights.Floor));
    addSequential(new CollectCargo());
  }
}