package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectHatchFromFeeder extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CollectHatchFromFeeder() {
    addSequential(new Commands.setTiltCommand(true))
    addSequential(new setLiftHeight(LiftHeights.fee));
    addSequential(new CollectHatch());
  }
}