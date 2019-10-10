package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.RobotComponents;
import frc.robot.RobotConstants;
import frc.robot.commands.DriveArcade;

/** This is the susbsystem for the drivetrain of the robot */
public class Drivetrain extends Subsystem {
  private SpeedControllerGroup leftDriveGroup, rightDriveGroup;
  private WPI_TalonSRX rightEncoder, lefEncoder;
  private DifferentialDrive drivetrain;
  private ADXRS450_Gyro gyro;
  private double prevTime = 0, leftAcceleration = 0, rightAcceleration = 0, currentTime = 0;
  private double TICKS_PER_METER =  RobotConstants.Sensors.DRIVETRAIN_ENCODERS_DISTANCE_PER_TICKS; 

  public Drivetrain() {
    this.leftDriveGroup = new SpeedControllerGroup(RobotComponents.Drivetrain.LEFT_FRONT_MOTOR,
        RobotComponents.Drivetrain.LEFT_MIDDLE_MOTOR, RobotComponents.Drivetrain.LEFT_REAR_MOTOR);
    this.rightDriveGroup = new SpeedControllerGroup(RobotComponents.Drivetrain.RIGHT_FRONT_MOTOR,
        RobotComponents.Drivetrain.RIGHT_MIDDLE_MOTOR, RobotComponents.Drivetrain.RIGHT_REAR_MOTOR);
    this.drivetrain = new DifferentialDrive(leftDriveGroup, rightDriveGroup);
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
    return getRightTicks() / TICKS_PER_METER;
  }

  public double getLeftDistance() {
    return getLeftTicks() / TICKS_PER_METER; 
  }

  public double getAverageDistance() {
    return (getRightDistance() + getLeftDistance()) / 2;
  }

  /** We devide the output of getSelectedSensorVelocity from tick per 0.1 second to meter per second */
  public double getRightVelocity() {
    return this.rightEncoder.getSelectedSensorVelocity() / (TICKS_PER_METER * 0.1);  
  }

  /** We devide the output of getSelectedSensorVelocity from tick per 0.1 second to meter per second */
  public double getLeftVelocity() {
    return this.lefEncoder.getSelectedSensorVelocity() / (TICKS_PER_METER * 0.1);
  }

  public double getAverageVelocity() {
    return (this.rightEncoder.getSelectedSensorVelocity() + this.lefEncoder.getSelectedSensorVelocity()) / 2;
  }

  public double getLeftAcceleration() {
    return this.leftAcceleration;
  }

  public double getRightAcceleration() {
    return this.rightAcceleration;
  }

  /** we calculate the acceleration of the robot in meter per second squared */
  @Override
  public void periodic() {
    currentTime = Timer.getFPGATimestamp();

    this.leftAcceleration = getLeftVelocity() / (currentTime - prevTime);
    this.rightAcceleration = getRightVelocity() / (currentTime - prevTime);

    this.prevTime = currentTime;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveArcade(() -> Robot.oi.driverXbox.getX(), () -> Robot.oi.driverXbox.getY()));
  }
}
