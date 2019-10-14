
package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Enums.LiftHeights;
import frc.robot.Enums.Target;
import frc.robot.RobotConstants;
import frc.robot.commands.*;

public class AutonomousCommand extends CommandGroup {
  public AutonomousCommand(boolean isLeft) {
    addSequential(new DriveStraight(RobotConstants.FieldDimensions.HAB_TO_CARGO_SHIP_DISTANCE));
    SetLiftHeight setLiftHeight = new SetLiftHeight(LiftHeights.CargoShip);
    addParallel(setLiftHeight);
    addSequential(new TurnWithGyro(isLeft ? 90 : -90));
    addSequential(new WaitUntil(setLiftHeight::isOnTarget));
    addSequential(new VisionPID(Target.CargoShip, true));
    addSequential(new EjectCargo());
  }
}