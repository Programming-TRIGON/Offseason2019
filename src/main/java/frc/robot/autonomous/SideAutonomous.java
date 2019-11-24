package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Enums.Path;
import frc.robot.command_group.PutHatch;
import frc.robot.commands.HatchHolderLock;
import frc.robot.motionprofiling.FollowPath;

public class SideAutonomous extends CommandGroup {
  /**
   * A clone of Orbit's #1690 side auto 
   */
  public SideAutonomous(boolean isLeft) {
    addSequential(new HatchHolderLock(true));
    addSequential(new FollowPath(Path.RAMP_TO_ROCKET, isLeft));
    addSequential(new WaitCommand(0.1));
    addSequential(new PutHatch(true));
    /*addSequential(new WaitCommand(0.1));
    addSequential(new FollowPath(Path.BACK_FROM_ROCKET, isLeft, true));
    addSequential(new WaitCommand(0.1));
    addSequential(new FollowPath(Path.ROCKET_TO_FEEDER, isLeft));
    addSequential(new WaitCommand(0.1));
    addSequential(new PutHatch(false));
    addSequential(new WaitCommand(0.1));
    addSequential(new FollowPath(Path.FEEDER_TO_ROCKET, isLeft));
    addSequential(new WaitCommand(0.1));
    addSequential(new PutHatch(true));
    addSequential(new WaitCommand(0.1));
    addSequential(new FollowPath(Path.BACK_FROM_ROCKET, !isLeft, true)); */
  }
}
