package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.setLiftHeight;
import frc.robot.RobotConstants;
import frc.robot.Enums.LiftHeights;
import frc.robot.Enums.Target;
import frc.robot.commands.EjectCargo;
import frc.robot.commands.VisionPID;


public class ScoreCargo extends CommandGroup {

  /** Scores the cargo. */
  public ScoreCargo(LiftHeights height) {
    Target target = RobotConstants.liftVisionMap.get(height);
    addParallel(new VisionPID(target, true));
    addSequential(new setLiftHeight(height));
    addSequential(new EjectCargo());
  }
}