
package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Enums.LiftHeights;
import frc.robot.Enums.Target;
import frc.robot.RobotConstants;
import frc.robot.RobotConstants.FieldDimensions;
import frc.robot.commands.*;

public class AutonomousCommand extends CommandGroup {
  public AutonomousCommand(boolean isLeft) {
    addSequential(new DriveStraight(RobotConstants.FieldDimensions.HAB_TO_CARGO_SHIP_DISTANCE));
    SetLiftHeight setLiftHeight = new SetLiftHeight(LiftHeights.CargoShip);
    addParallel(setLiftHeight);
    addSequential(new TurnWithGyro(isLeft ?1:-1 * FieldDimensions.AUTO_TURN_ANGLE));
    addSequential(new WaitUntil(setLiftHeight::isOnTarget));
    addSequential(new VisionPID(Target.CargoShip, true));
    addSequential(new EjectCargo());
    this.clearRequirements();
  }
}