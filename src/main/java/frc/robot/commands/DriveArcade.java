package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import java.util.function.Supplier;

public class DriveArcade extends Command {
  private Supplier<Double> x, y;
  private static final double SENSITIVITY = 1;
  private static final double THRESHOLD = 0.5;
  private static final double DEADBAND = 0.095;
  private static boolean drive = true;

  public DriveArcade(Supplier<Double> x, Supplier<Double> y) {
    requires(Robot.drivetrain);
    this.x = () -> calculateDeadband(x.get());
    this.y = () -> calculateDeadband(y.get());
  }

  public DriveArcade(Supplier<Double> x, Supplier<Double> forward, Supplier<Double> reverse) {
    requires(Robot.drivetrain);
    this.x = () -> calculateDeadband(x.get());
    this.y = () -> rootFunction(forward.get() - reverse.get());
  }


  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    double y = this.y.get();
    double x = this.x.get();

    if(drive)
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
    //value -= Math.signum(0.05);
    //return Math.signum(value) * (2 * Math.sqrt(Math.abs(value)) - Math.abs(value));
    boolean isLiniar = Math.abs(value) <= 0.25;
    return isLiniar ? 2 * value : Math.signum(value) * Math.sqrt(Math.abs(value));
  }

  private static double calculateDeadband(double value) {
    if (Math.abs(value) < DEADBAND)
      return 0;
    return value;
  }

  public static void toggleDrive() {
    drive = !drive;
  }
}
