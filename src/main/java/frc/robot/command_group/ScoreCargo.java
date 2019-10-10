package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.setLiftHeight;
import frc.robot.Enums.LiftHeights;
import frc.robot.commands.EjectCargo;
import frc.robot.commands.Commands;


public class ScoreCargo extends CommandGroup {

  /** Scores the cargo. */
  public ScoreCargo(LiftHeights height) {
    addSequential(Commands.setTiltCommand(true));
    addSequential(new setLiftHeight(height));
    addSequential(new EjectCargo());
  }
}