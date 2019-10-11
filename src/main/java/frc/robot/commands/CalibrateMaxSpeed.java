package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CalibrateMaxSpeed extends Command {
  double leftForwardMaxSpeed = 0;
  double leftReverseMaxSpeed = 0;
  double rightForwardMaxSpeed = 0;
  double rightReverseMaxSpeed = 0;
  double leftForwardAcc = 0;
  double leftReverseAcc = 0;
  double rightForwardAcc = 0;
  double rightReverseAcc = 0;
  boolean isReversed = false;
  Button button;
  DoubleSupplier forwardSupplier, rotationSupplier;
  public CalibrateMaxSpeed(boolean isReversed, Button button, DoubleSupplier forwardSupplier, DoubleSupplier rotationSupplier) {
    this.isReversed = isReversed;
    this.button = button;
    this.forwardSupplier = forwardSupplier;
    this.rotationSupplier = rotationSupplier;
  }
  
  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.drivetrain.arcadeDrive(this.rotationSupplier.getAsDouble(), this.forwardSupplier.getAsDouble());
    if(this.isReversed){
      if(Robot.drivetrain.getRightVelocity() < rightReverseMaxSpeed) {
        rightReverseMaxSpeed = Robot.drivetrain.getRightVelocity();
        rightReverseAcc = Robot.drivetrain.getRightAcceleration();
      }
      if(Robot.drivetrain.getLeftVelocity() < leftReverseMaxSpeed) {
        leftReverseMaxSpeed = Robot.drivetrain.getLeftVelocity();
        leftReverseAcc = Robot.drivetrain.getLeftAcceleration();
      }
    } else {
      if(Robot.drivetrain.getRightVelocity() > rightForwardMaxSpeed) {
        rightForwardMaxSpeed = Robot.drivetrain.getRightVelocity();
        rightForwardAcc = Robot.drivetrain.getRightAcceleration();
      }
      if(Robot.drivetrain.getLeftVelocity() > leftForwardMaxSpeed) {
        leftForwardMaxSpeed = Robot.drivetrain.getLeftVelocity();
        leftForwardAcc = Robot.drivetrain.getLeftAcceleration();
      }
    }
  }
  @Override
  protected boolean isFinished() {
    return this.button.get();
  }
  @Override
  protected void end() {
    Robot.drivetrain.arcadeDrive(0, 0);
    if(isReversed){
      System.out.println("Right reverse max speed: " + rightReverseMaxSpeed);
      System.out.println("Left reverse max speed: " + leftReverseMaxSpeed);
      System.out.println("Right reverse acc: " + rightReverseAcc);
      System.out.println("Left reverse acc: " + leftReverseAcc);
    }
    else{
      System.out.println("Right forward max speed: " + rightForwardMaxSpeed);
      System.out.println("Left forward max speed: " + leftForwardMaxSpeed);
      System.out.println("Right forward acc: " + rightForwardAcc);
      System.out.println("Left forward acc: " + leftForwardAcc); 
    }
  }

  @Override
  protected void interrupted() {
    end();
  }
}
