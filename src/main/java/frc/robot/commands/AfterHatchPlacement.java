package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AfterHatchPlacement extends Command {
  boolean isLock;
  private Supplier<Double> y;

  public AfterHatchPlacement(Supplier<Double> forward, Supplier<Double> reverse) {
    requires(Robot.hatchHolder);
    requires(Robot.drivetrain);
    y = () -> rootFunction((forward.get() - reverse.get()));
  }

  @Override
  protected void initialize() {
    isLock = Robot.hatchHolder.isLock();
    Robot.drivetrain.setXLock(true);
  }

  @Override
  protected void execute() {
    double y = this.y.get();

    if(Robot.drivetrain.getCanDrive())
      Robot.drivetrain.arcadeDrive(0, y);
    else 
      Robot.drivetrain.tankDrive(0, 0);
  }

  @Override
  protected boolean isFinished() {
    return Robot.limelight.getDistance() >= 8 || !Robot.limelight.getTv() || !isLock;
  }

  @Override
  protected void end() {
    Robot.drivetrain.setXLock(false);
    Robot.hatchHolder.setLock(true);
  }

  @Override
  protected void interrupted() {
    end();
  }

  private double rootFunction(double value) {
    boolean isLinear = Math.abs(value) <= 0.25;
    return isLinear ? 2 * value : Math.signum(value) * Math.sqrt(Math.abs(value));
  }
}
