package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveArcade extends Command {
  private Supplier<Double> x, y;
  private final double SENSETIVITY = 1;
  private final double THRESHOLD = 0.5;
  private final double DEADBAND = 0.095;

  public DriveArcade(Supplier<Double> x, Supplier<Double> y) {
    requires(Robot.drivetrain);
    this.x = () -> x.get() - DEADBAND;
    this.y = () -> y.get() - DEADBAND;
  }

  public DriveArcade(Supplier<Double> x, Supplier<Double> forward, Supplier<Double> reverse) {
    requires(Robot.drivetrain);
    this.x = x;
    this.y = () -> 1.25 * forward.get() - 1.25 * reverse.get();
  }


  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    double y = this.y.get();
    double x = this.x.get();
    //System.out.println("X: " + x + " Y: " + y);
    Robot.drivetrain.curvatureDrive(SENSETIVITY * x, SENSETIVITY * y, inRange(y, -THRESHOLD, THRESHOLD) && inRange(x, -THRESHOLD, THRESHOLD));
    /*if (inRange(y, -DEAD_BAND, DEAD_BAND) && inRange(x, -DEAD_BAND, DEAD_BAND)) {
      Robot.drivetrain.curvatureDrive(0, 0, inRange(y, -THRESHOLD, THRESHOLD));
    } else if(inRange(y, -DEAD_BAND, DEAD_BAND)) {
      Robot.drivetrain.curvatureDrive(SENSETIVITY * x, 0, inRange(y, -THRESHOLD, THRESHOLD));
    } else if(inRange(x, -DEAD_BAND, DEAD_BAND)) {
      Robot.drivetrain.curvatureDrive(0, SENSETIVITY * y, inRange(y, -THRESHOLD, THRESHOLD));
    } else {
      Robot.drivetrain.curvatureDrive(SENSETIVITY * x, SENSETIVITY * y, inRange(y, -THRESHOLD, THRESHOLD));
    }*/
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.drivetrain.arcadeDrive(0, 0);
  }

  @Override
  protected void interrupted() {
    end();
  }

  private boolean inRange(double val, double min, double max) {
    return (val >= min) && (val <= max);
  }
}
