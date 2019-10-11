package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.utils.Logger;

import java.util.function.Supplier;


public class CalibrateDistance extends Command {
    private Supplier<Boolean> wantToLog;
    private boolean isPressed = false; 
    private double deltaDistance;
    private double currentDistance = 0;
    private Logger logger;

    public CalibrateDistance(Supplier<Boolean> wantToLog) {
        this(wantToLog, 15);
    }

    public CalibrateDistance(Supplier<Boolean> wantToLog, double deltaDistance) {
        setRunWhenDisabled(true);
        this.wantToLog = wantToLog;
        this.deltaDistance = deltaDistance;
    }

    @Override
    protected void initialize() {
        logger = new Logger("distance calibration.csv", "height", "Distance");
    }


    @Override
    protected void execute() {
        if (wantToLog.get()) {
            if(!isPressed){
            isPressed = true;
            logger.log(Robot.limelight.getTy(), currentDistance);
            currentDistance += deltaDistance;
            }
        }
        else
        isPressed = false;
    }

    @Override
    protected boolean isFinished() {
        return currentDistance > deltaDistance*6;

    }

    @Override
    protected void end() {
        logger.close();

    }

    @Override
    protected void interrupted() {
        end();

    }
}

