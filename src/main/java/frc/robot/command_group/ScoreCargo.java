package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.commands.SetLiftHeight;
import frc.robot.commands.WaitUntil;
import frc.robot.RobotConstants;
import frc.robot.Enums.LiftHeights;
import frc.robot.Enums.Target;
import frc.robot.commands.EjectCargo;
import frc.robot.commands.VisionPID;

public class ScoreCargo extends CommandGroup {

  /** Scores the cargo. */
  public ScoreCargo(LiftHeights height, Target target) {
    SetLiftHeight setLiftHeight = new SetLiftHeight(height);
    addParallel(setLiftHeight);
    addSequential(new VisionPID(target, true));
    addSequential(new WaitUntil(setLiftHeight::isOnTarget));
    addSequential(new EjectCargo());
  }
}