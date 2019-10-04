package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotComponents;
import frc.robot.RobotConstants;

public class TurnWithGyro extends Command {
  PIDController pidController;
  PidSettings pidSettings;
  double angle, timeOnTarget, timeOnTarget;

  public TurnWithGyro(double angle, PidSettings pidSettings) {
    requires(Robot.drivetrain);
    this.angle = angle;
    this.pidSettings = pidSettings;
  }

  public TurnWithGyro(double angle){
    this(angle, RobotConstants.RobotPIDSettings.DRIVETRAIN_TURN_PID_SETTINGS);
  }

  @Override
  protected void initialize() {
    this.pidController = new PIDController(pidSettings.getKP(), pidSettings.getKI(), pidSettings.getKD(), 
    RobotComponents.Drivetrain.GYRO, output -> Robot.drivetrain.arcadeDrive(output, 0));
    pidController.setContinuous();
    pidController.setOutputRange(-1, 1);
    pidController.setAbsoluteTolerance(pidSettings.getTolerance());
    pidController.setSetpoint(angle);
    pidController.enable();
  }

  @Override
  protected void execute() {
    if(!pidController.onTarget())
      timeOnTarget = Timer.getFPGATimestamp();
  }

  @Override
  protected boolean isFinished() {
    return (Timer.getFPGATimestamp() - timeOnTarget) > pidSettings.getWaitTime();
  }

  @Override
  protected void end() {
    Robot.drivetrain.arcadeDrive(0, 0);
    pidController.close();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
