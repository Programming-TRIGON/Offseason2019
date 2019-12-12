package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import java.util.function.Supplier;

public class DriveArcade extends Command {
  private Supplier<Double> x, y;
  private static final double SENSITIVITY = 1;
  private static final double THRESHOLD = 0.5;
  private static final double DEADBAND = 0.095;

  public DriveArcade(Supplier<Double> x, Supplier<Double> y) {
    requires(Robot.drivetrain);
    this.x = () -> x.get();
    this.y = () -> y.get();
  }

  public DriveArcade(Supplier<Double> x, Supplier<Double> forward, Supplier<Double> reverse) {
    requires(Robot.drivetrain);
    this.x = () -> xRootFunction(x.get());
    this.y = () -> rootFunction(forward.get() - reverse.get());
  }


  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    double y = this.y.get();
    double x = this.x.get();

    if(Robot.drivetrain.getCanDrive())
      Robot.drivetrain.curvatureDrive(SENSITIVITY * x, SENSITIVITY * y, Math.sqrt(y * y + x * x) < THRESHOLD || Math.abs(y) < Math.abs(x));
    else 
      Robot.drivetrain.tankDrive(0, 0);
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

  private double rootFunction(double value) {
    boolean isLinear = Math.abs(value) <= 0.25;
    return isLinear ? 2 * value : Math.signum(value) * Math.sqrt(Math.abs(value));
  }

  private double xRootFunction(double value) {
    boolean isLinear = Math.abs(value) <= 0.5;
    return Robot.drivetrain.getIsXLock() ? 0 : isLinear ? 0.5 * value : Math.signum(value) * Math.pow(value, 2);
  }

  private static double calculateDeadband(double value) {
    if(Math.abs(value) < DEADBAND) {
      return 0;
    }
    return value;
  }
}
