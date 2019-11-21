/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotComponents;

import static frc.robot.Robot.drivetrain;

public class CalibrateFeedForward extends Command {
  public CalibrateFeedForward() {
    requires(drivetrain);
  }
  NetworkTableEntry autoSpeedEntry =
    NetworkTableInstance.getDefault().getEntry("/robot/autospeed");
  NetworkTableEntry telemetryEntry =
    NetworkTableInstance.getDefault().getEntry("/robot/telemetry");
    double priorAutospeed = 0;
    Number[] numberArray = new Number[9];

    public void update() {
        double now = Timer.getFPGATimestamp();
    
        double leftPosition = Robot.drivetrain.getLeftDistance();
        double leftRate = Robot.drivetrain.getLeftVelocity();
    
        double rightPosition = Robot.drivetrain.getRightDistance();
        double rightRate = Robot.drivetrain.getRightVelocity();
    
        double battery = RobotController.getBatteryVoltage();
    
        double leftMotorVolts = RobotComponents.Drivetrain.LEFT_FRONT_MOTOR.getBusVoltage()
            * RobotComponents.Drivetrain.LEFT_FRONT_MOTOR.getAppliedOutput();
        double rightMotorVolts = RobotComponents.Drivetrain.RIGHT_FRONT_MOTOR.getBusVoltage()
            * RobotComponents.Drivetrain.RIGHT_FRONT_MOTOR.getAppliedOutput();
    
        // Retrieve the commanded speed from NetworkTables
        double autospeed = autoSpeedEntry.getDouble(0);
        priorAutospeed = autospeed;
    
        // command motors to do things
        Robot.drivetrain.tankDrive(autospeed, autospeed);
    
        // send telemetry data array back to NT
        numberArray[0] = now;
        numberArray[1] = battery;
        numberArray[2] = autospeed;
        numberArray[3] = leftMotorVolts;
        numberArray[4] = rightMotorVolts;
        numberArray[5] = leftPosition;
        numberArray[6] = rightPosition;
        numberArray[7] = leftRate;
        numberArray[8] = rightRate;
    
        telemetryEntry.setNumberArray(numberArray);
      }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    update();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
