package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveArcade extends Command {
  private Supplier<Double> x, y;

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
    Robot.drivetrain.curvatureDrive(RobotStates.isDriveInverted() ? this.sensetivity * y
    : -this.sensetivity * y, this.rotentionSupplier.get() * this.sensetivity);
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
