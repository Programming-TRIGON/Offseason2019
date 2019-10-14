
package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.motionprofiling.FollowPath;
import frc.robot.Enums.Path;
import frc.robot.commands.TurnWithGyro;

public class AutonomousCommand extends CommandGroup {
  public AutonomousCommand(boolean isLeft) {
    //addParallel(tilt(down));
    //addParallel(new lift(up));
    if(isLeft)
      addSequential(new FollowPath(Path.SHIP));
    else
        addSequential(new FollowPath(Path.SHIP,true));
    //addSequential(new FollowTarget(ship));
    //addSequential(new EjectCargo);
    //addParallel(new lift(down));
    if(isLeft)
      addSequential(new FollowPath(Path.PATH2,false,true));
    else
        addSequential(new FollowPath(Path.PATH2,true,true));
    addSequential(new TurnWithGyro(120));
    //addSequential(new FollowTarget(feeder));
    //addSequential(new CollectHatch);
    if(isLeft)
      addSequential(new FollowPath(Path.PATH3,false,true));
    else
        addSequential(new FollowPath(Path.PATH3,true,true));
    addSequential(new TurnWithGyro(90));
    //addSequential(new FollowTarget(Rocket));
    //addSequential(new ejectHatch);
  }
}