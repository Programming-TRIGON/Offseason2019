
package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.motionprofiling.FollowPath;
import frc.robot.Enums.Path;

public class AutonomousCommand extends CommandGroup {
  public AutonomousCommand(boolean isLeft) {
    if(isLeft)
      addSequential(new FollowPath(Path.SHIP));
    else
        addSequential(new FollowPath(Path.SHIP,true));
  }
}