package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotComponents;

/** This is the susbsystem for the drivetrain of the robot */
public class Drivetrain extends Subsystem {
  private SpeedControllerGroup leftDriveMotors, rightDriveMotors;
  private DifferentialDrive drivetrain;
  private ADXRS450_Gyro gyro;

  public Drivetrain() {
    this.leftDriveMotors = new SpeedControllerGroup(RobotComponents.Drivetrain.LEFT_FRONT_MOTOR,
        RobotComponents.Drivetrain.LEFT_MIDDLE_MOTOR, RobotComponents.Drivetrain.LEFT_REAR_MOTOR);
    this.rightDriveMotors = new SpeedControllerGroup(RobotComponents.Drivetrain.RIGHT_FRONT_MOTOR,
        RobotComponents.Drivetrain.RIGHT_MIDDLE_MOTOR, RobotComponents.Drivetrain.RIGHT_REAR_MOTOR);
    this.drivetrain = new DifferentialDrive(leftDriveMotors, rightDriveMotors);
    this.gyro = RobotComponents.Drivetrain.GYRO;
  }

  public void arcadeDrive(double x, double y){
    this.drivetrain.arcadeDrive(y, x);
  }

  public void tankDrive(double leftSpeed, double rightSpeed){
    this.drivetrain.tankDrive(leftSpeed, rightSpeed);
  }

  public void curvatureDrive(double x, double y, boolean quickTurn){
    this.drivetrain.curvatureDrive(y, x, quickTurn);
  }

  public double getAngle(){
    return this.gyro.getAngle();
  }

  @Override
  public void initDefaultCommand() {
  }
}
