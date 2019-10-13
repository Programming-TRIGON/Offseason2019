package frc.robot.command_group;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import frc.robot.Enums;
import frc.robot.Enums.LiftHeights;
import frc.robot.Enums.ScoreHeight;
import frc.robot.Robot;

public class Score extends CommandGroup {
    public Score(ScoreHeight height) {
        LiftHeights cargoHeight = null, hatchHeight = null;
        switch (height) {
            case kLow:
                cargoHeight = LiftHeights.RocketCargoBottom;
                hatchHeight = LiftHeights.HatchBottom;
                break;
            case kMedium:
                cargoHeight = LiftHeights.RocketCargoMiddle;
                hatchHeight = LiftHeights.RocketHatchMiddle;
                break;
            case kHigh:
                cargoHeight = LiftHeights.RocketCargoTop;
                hatchHeight = LiftHeights.RocketHatchTop;
                break;
            case kCargoShip:
                return;
        }
        addSequential(new ConditionalCommand
                (new ScoreCargo(cargoHeight, Enums.Target.RocketMiddle),
                new HatchScore(hatchHeight, Enums.Target.RocketSide, true)) {
            @Override
            protected boolean condition() {
                return Robot.cargoHolder.isCargoCollected();
            }
        });
    }
}
