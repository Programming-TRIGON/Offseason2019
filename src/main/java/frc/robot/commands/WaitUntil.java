package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import java.util.function.BooleanSupplier;

public class WaitUntil extends Command {
    private BooleanSupplier endCondition;

    public WaitUntil(BooleanSupplier endCondition){
        this.endCondition = endCondition;
    }
    @Override
    protected boolean isFinished() {
        return endCondition.getAsBoolean();
    }
}
