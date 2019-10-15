package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import frc.robot.Enums.LiftHeights;
import frc.robot.Enums.ScoreHeight;
import frc.robot.Robot;
import frc.robot.commands.SetLiftHeight;

public class PrepareToScore extends CommandGroup {
    public PrepareToScore(ScoreHeight height) {
        LiftHeights cargoHeight = null, hatchHeight = null;
        switch (height) {
            case kLow:
                cargoHeight = LiftHeights.RocketCargoBottom;
                hatchHeight = LiftHeights.HatchBottom;
                break;
            case kMedium:
                cargoHeight = LiftHeights.CargoShip;
                hatchHeight = LiftHeights.RocketHatchMiddle;
                break;
            case kMediumGrossman:
                cargoHeight = LiftHeights.RocketCargoMiddle;
                hatchHeight = LiftHeights.RocketHatchMiddle;
                break;
            case kHigh:
                cargoHeight = LiftHeights.RocketCargoMiddle;
                hatchHeight = LiftHeights.RocketHatchTop;
                break;
        }
        addSequential(new ConditionalCommand(new SetLiftHeight(cargoHeight), new SetLiftHeight(hatchHeight))
        {
            @Override
            protected boolean condition() {
                return Robot.cargoHolder.isCargoCollected(); //Change the condition to this when we have hatch subsystem 
            }
        });
    }
}
