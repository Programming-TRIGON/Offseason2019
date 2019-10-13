package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotComponents;
import frc.robot.RobotConstants;
import frc.robot.pidsources.DriveForwardPidSource;

/** Drive straight with encoders and gyro. */
public class DriveStraight extends Command {
  private PidSettings pidSettingsX, pidSettingsY;
  private PIDController pidControllerX, pidControllerY;
  private double timeOnTarget, setpoint, outputX, outputY;
  private PIDSource drivePidSource;

  public DriveStraight(double setpoint, PidSettings pidSettingsX, PidSettings pidSettingsY) {
    requires(Robot.drivetrain);
    this.setpoint = setpoint;
    this.pidSettingsX = pidSettingsX;
    this.pidSettingsY = pidSettingsY;
  }

  public DriveStraight(double setpoint) {
    this(setpoint, RobotConstants.RobotPIDSettings.DRIVETRAIN_TURN_PID_SETTINGS,
        RobotConstants.RobotPIDSettings.DRIVE_FORWARD_PID_SETTINGS);
  }

  @Override
  protected void initialize() {
    drivePidSource = new DriveForwardPidSource();
    this.pidControllerY = new PIDController(pidSettingsY.getKP(), pidSettingsY.getKI(), pidSettingsY.getKD(),
        drivePidSource, output -> outputY = output);
    pidControllerY.setOutputRange(-1, 1);
    pidControllerY.setAbsoluteTolerance(pidSettingsY.getTolerance());
    pidControllerY.setSetpoint(this.setpoint);

    
    this.pidControllerX = new PIDController(pidSettingsX.getKP(), pidControllerX.getI(), pidControllerX.getD(),
        RobotComponents.Drivetrain.GYRO, output -> outputX = output);
    pidControllerX.setOutputRange(-1, 1);
    pidControllerX.setAbsoluteTolerance(pidSettingsX.getTolerance());
    pidControllerX.setSetpoint(Robot.drivetrain.getAngle());
    
    Robot.drivetrain.resetEncoders();

    pidControllerX.enable(); 
    pidControllerY.enable();
  }

  @Override
  protected void execute() {
    Robot.drivetrain.arcadeDrive(outputX, outputY);

    if (!pidControllerY.onTarget())
      timeOnTarget = Timer.getFPGATimestamp();
  }

  @Override
  protected boolean isFinished() {
    return (Timer.getFPGATimestamp() - timeOnTarget) > pidSettingsY.getWaitTime();
  }

  @Override
  protected void end() {
    pidControllerX.disable();
    pidControllerY.disable();
    pidControllerX.close();
    pidControllerY.close();
    Robot.drivetrain.arcadeDrive(0, 0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}