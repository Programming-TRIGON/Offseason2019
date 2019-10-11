package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PidSettings;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.Enums.LiftHeights;
import frc.robot.pidsources.LiftEncoderPidSource;

public class SetLiftHeight extends Command {
  private double liftHeight;
  private PIDController pidController;

  /** This command moves the lift to the desired height and stay there */
  public SetLiftHeight(LiftHeights liftHeight, PidSettings pidSettings) {
    requires(Robot.lift);
    this.liftHeight = liftHeight.getHeight();
    pidController = new PIDController(pidSettings.getKP(), pidSettings.getKI(), pidSettings.getKD(),
            new LiftEncoderPidSource(), output -> Robot.lift.setMotorsPower(output));
    pidController.setSetpoint(this.liftHeight);
    pidController.setOutputRange(-1, 1);
  }

  public SetLiftHeight(LiftHeights liftHeight) {
    this(liftHeight, RobotConstants.RobotPIDSettings.LIFT_PID_SETTINGS);
  }

  @Override
  protected void initialize() {
    if(liftHeight > RobotConstants.RobotDimensions.SAFETY_HEIGHT)
      Robot.cargoHolder.setTilt(true);
    pidController.reset();
    pidController.enable();
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
    pidController.disable();
    pidController.close();
    Robot.lift.setMotorsPower(0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
