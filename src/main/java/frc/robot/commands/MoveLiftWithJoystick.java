package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveLiftWithJoystick extends Command {
  Supplier<Double> power;

  public MoveLiftWithJoystick(Supplier<Double> power) {
    requires(Robot.lift);
    this.power = power;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.lift.setMotorsPower(this.power.get());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.lift.setMotorsPower(0s);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
