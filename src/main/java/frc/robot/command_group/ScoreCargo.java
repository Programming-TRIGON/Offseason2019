package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreCargo extends CommandGroup {

  /** Scores the cargo. */
  public ScoreCargo(LiftHeights height) {
    addSequential(Commands.)
    addSequential(new setLiftHeight(height));
  }
}
