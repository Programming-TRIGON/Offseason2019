package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.GyroPIDSource;
import frc.robot.Robot;

public class DriveArcadeWithGyro extends Command {
  private PIDSource pidSource; 
  private PIDController pidController;

  public DriveArcadeWithGyro() {
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
    this.pidSource = new GyroPIDSource();
  }

  @Override
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
    end();
  }
}
