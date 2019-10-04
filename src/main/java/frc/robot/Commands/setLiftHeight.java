package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Enums;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.pidSources.LiftEncoderPidSource;

public class setLiftHeight extends Command {
  private PidSettings pidSettings;
  private double setpoint, waitTime, timeOnTarget;
  private PIDController pidController;

  /** This command moves the lift to the desired setpoint */
  public setLiftHeight(Enums.LiftHeights setpoint, PidSettings pidSettings) {
    requires(Robot.lift);
    this.pidSettings = pidSettings;
    this.setpoint = setpoint.getHeight();
  }

  public setLiftHeight(Enums.LiftHeights setpoint) {
    this(setpoint, RobotConstants.RobotPIDSettings.LIFT_PID_SETTINGS);
  }

  @Override
  protected void initialize() {
    this.pidController = new PIDController(pidSettings.getKP(), pidSettings.getKD(), pidSettings.getKD(),
        new LiftEncoderPidSource(), output -> Robot.lift.setMotorsPower(output));
    this.pidController.setSetpoint(this.setpoint);
    this.pidController.setAbsoluteTolerance(this.pidSettings.getTolerance());
    this.waitTime = this.pidSettings.getWaitTime();
    this.pidController.setOutputRange(-1, 1);
    this.timeOnTarget = Timer.getFPGATimestamp();
    this.pidController.enable();

  }

  @Override
  protected void execute() {
    if (this.pidController.onTarget())
      this.timeOnTarget = Timer.getFPGATimestamp();
  }

  @Override
  protected boolean isFinished() {
    return Timer.getFPGATimestamp() - this.timeOnTarget >= this.waitTime;
  }

  @Override
  protected void end() {
    this.pidController.disable();
    this.pidController.close();
    Robot.lift.setMotorsPower(0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
