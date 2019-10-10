package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Enums.LiftHeights;
import frc.robot.Enums.Target;
import frc.robot.commands.VisionPID;
import frc.robot.commands.setLiftHeight;

/**
 * this command group scores the hatch: with vision, changes the lifts height and then scores
 */
public class HatchScore extends CommandGroup {
  public HatchScore(LiftHeights liftHeight, Target target, boolean isDrivingForward) {
    addParallel(new VisionPID(target, isDrivingForward));
    addSequential(new setLiftHeight(liftHeight));
    addSequential(new EjectHatch());
  }
}
