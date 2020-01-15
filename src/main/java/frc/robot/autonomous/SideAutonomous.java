package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Enums.Path;
import frc.robot.Enums.RamsetePath;
import frc.robot.Robot;
import frc.robot.command_group.PutHatch;
import frc.robot.commands.Commands;
import frc.robot.commands.HatchHolderLock;
import frc.robot.motionprofiling.FollowPath;
import frc.robot.motionprofiling.RamseteFollowPath;

public class SideAutonomous extends CommandGroup {
  /**
   * A clone of Orbit's #1690 side auto
   */
  public SideAutonomous(boolean isLeft) {
    addSequential(Commands.resetGyro());
    addSequential(new InstantCommand(() -> Robot.drivetrain.resetOdometry(RamsetePath.RAMP_TO_ROCKET.getTrajectory().getInitialPose())));
    addSequential(new HatchHolderLock(true));
    addSequential(new RamseteFollowPath(RamsetePath.RAMP_TO_ROCKET));
    addSequential(new PutHatch(true));
    addSequential(new RamseteFollowPath(RamsetePath.BACK_FROM_ROCKET, true));
    addSequential(new RamseteFollowPath(RamsetePath.ROCKET_TO_FEEDER));
    addSequential(new PutHatch(false));
    addSequential(new RamseteFollowPath(RamsetePath.FEEDER_TO_ROCKET, true));
    addSequential(new PutHatch(true));
    addSequential(new RamseteFollowPath(RamsetePath.BACK_FROM_FAR_SIDE, true));
  }
}
