package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotComponents;

/** This is the susbsystem for the drivetrain of the robot */
public class Drivetrain extends Subsystem {
  private SpeedControllerGroup leftDriveMotors, rightDriveMotors;
  private WPI_TalonSRX rightEncoder, lefEncoder;
  private DifferentialDrive drivetrain;
  private ADXRS450_Gyro gyro;
  private double prevTime=0, leftAcceleration=0, rightAcceleration=0;


  public Drivetrain() {
    this.leftDriveMotors = new SpeedControllerGroup(RobotComponents.Drivetrain.LEFT_FRONT_MOTOR,
        RobotComponents.Drivetrain.LEFT_MIDDLE_MOTOR, RobotComponents.Drivetrain.LEFT_REAR_MOTOR);
    this.rightDriveMotors = new SpeedControllerGroup(RobotComponents.Drivetrain.RIGHT_FRONT_MOTOR,
        RobotComponents.Drivetrain.RIGHT_MIDDLE_MOTOR, RobotComponents.Drivetrain.RIGHT_REAR_MOTOR);
    this.drivetrain = new DifferentialDrive(leftDriveMotors, rightDriveMotors);
    this.gyro = RobotComponents.Drivetrain.GYRO;
    this.rightEncoder = RobotComponents.Drivetrain.RIGHT_ENCODER_PLACEHOLDER;
    this.lefEncoder = RobotComponents.Drivetrain.LEFT_ENCODER_PLACEHOLDER;
  }

  public void arcadeDrive(double x, double y) {
    this.drivetrain.arcadeDrive(y, x);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    this.drivetrain.tankDrive(leftSpeed, rightSpeed);
  }

  public void curvatureDrive(double x, double y, boolean quickTurn) {
    this.drivetrain.curvatureDrive(y, x, quickTurn);
  }

  public double getAngle() {
    return this.gyro.getAngle();
  }

  public void resetGyro() {
    this.gyro.reset();
  }

  public void calibrateGyro() {
    this.gyro.calibrate();
  }

  public int getLeftTicks() {
    return this.lefEncoder.getSelectedSensorPosition();
  }

  public int getRightTicks() {
    return this.rightEncoder.getSelectedSensorPosition();
  }

  public void resetEncoders() {
    this.lefEncoder.setSelectedSensorPosition(0);
    this.rightEncoder.setSelectedSensorPosition(0);
  }

  public double getRightDistance() {
    return getRightTicks(); // divide by constant
  }

  public double getLeftDistance() {
    return getLeftTicks(); // divide by constant
  }

  public double getAverageDistance() {
    return getRightDistance() + getLeftDistance() / 2; // divide by constant
  }

  public double getRightVelocity(){
    return this.rightEncoder.getSelectedSensorVelocity();
  }

  public double getLeftVelocity(){
    return this.lefEncoder.getSelectedSensorVelocity();
  }

  public double getAverageVelocity(){
    return this.rightEncoder.getSelectedSensorVelocity() + this.lefEncoder.getSelectedSensorVelocity();
  }

    /** we calculate the acceleration of the robot as well as its velocity*/
    @Override
    public void periodic() {
      double currentTime = Timer.getFPGATimestamp();
  
      this.leftAcceleration = getLeftVelocity() / (currentTime - prevTime);
      this.rightAcceleration = getRightVelocity() / (currentTime - prevTime);
  
      this.prevTime = currentTime;
    }

  @Override
  public void initDefaultCommand() {
  }
}
