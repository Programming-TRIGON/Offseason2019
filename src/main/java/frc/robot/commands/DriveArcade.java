package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveArcade extends Command {
  private Supplier<Double> x, y;
  private final double SENSETIVITY = 1.15;
  private final double THRESHOLD = 0.5;  

  public DriveArcade(Supplier<Double> x, Supplier<Double> y) {
    requires(Robot.drivetrain);
    this.x = x;
    this.y = y;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    double y = this.y.get();
    Robot.drivetrain.curvatureDrive(SENSETIVITY * x.get(), SENSETIVITY * y, y <= THRESHOLD);
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
}
