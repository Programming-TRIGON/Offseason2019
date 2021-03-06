package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveLiftWithJoystick extends Command {
  private Supplier<Double> power;

  /**
   * @param power supplier for how much power should be put into the motors.
   */
  public MoveLiftWithJoystick(Supplier<Double> power) {
    requires(Robot.lift);
    this.power = power;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    //updates the motor according to the supplier.
    Robot.lift.setMotorsPower(0.5 * this.power.get());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.lift.setMotorsPower(0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
