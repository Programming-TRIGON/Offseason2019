package frc.robot.subsystems;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.RobotComponents;
import frc.robot.RobotConstants;
import frc.robot.commands.DriveArcade;

/** This is the susbsystem for the drivetrain of the robot */
public class Drivetrain extends Subsystem {
    private SpeedControllerGroup leftDriveGroup, rightDriveGroup;
    private WPI_TalonSRX rightEncoder, leftEncoder;
    private DifferentialDrive drivetrain;
    private ADIS16448_IMU gyro;
    private double prevTime = 0, leftAcceleration = 0, rightAcceleration = 0, currentTime = 0, prevLeftVelocity = 0,
      prevRightVelocity = 0;
    private double TICKS_PER_METER = RobotConstants.Sensors.DRIVETRAIN_ENCODERS_DISTANCE_PER_TICKS;
    private final double RAMP_LIMIT = 1; // In sesconds, to full speed

  public Drivetrain() {
    // Settings for each side of the robot 
    setSparksSettings(RobotComponents.Drivetrain.LEFT_FRONT_MOTOR,
      RobotComponents.Drivetrain.LEFT_MIDDLE_MOTOR, RobotComponents.Drivetrain.LEFT_REAR_MOTOR);
    setSparksSettings(RobotComponents.Drivetrain.RIGHT_FRONT_MOTOR,
      RobotComponents.Drivetrain.RIGHT_MIDDLE_MOTOR, RobotComponents.Drivetrain.RIGHT_REAR_MOTOR); 
      
    this.leftDriveGroup = new SpeedControllerGroup(RobotComponents.Drivetrain.LEFT_FRONT_MOTOR,
      RobotComponents.Drivetrain.LEFT_MIDDLE_MOTOR, RobotComponents.Drivetrain.LEFT_REAR_MOTOR); 
    this.rightDriveGroup = new SpeedControllerGroup(RobotComponents.Drivetrain.RIGHT_FRONT_MOTOR,
      RobotComponents.Drivetrain.RIGHT_MIDDLE_MOTOR, RobotComponents.Drivetrain.RIGHT_REAR_MOTOR); 

        this.drivetrain = new DifferentialDrive(this.leftDriveGroup, this.rightDriveGroup);

        this.gyro = RobotComponents.Drivetrain.GYRO;

        this.rightEncoder = RobotComponents.Drivetrain.ENCODER_RIGHT;
        this.leftEncoder = RobotComponents.CargoCollector.HOLDER_MOTOR;
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
        return this.gyro.getAngleZ();
    }

  public void resetGyro() {
    this.gyro.reset();
  }

  public void calibrateGyro() {
    this.gyro.calibrate();
  }

  public int getLeftTicks() {
    return this.leftEncoder.getSelectedSensorPosition();
  }

  public int getRightTicks() {
    return this.rightEncoder.getSelectedSensorPosition();
  }

  public void resetEncoders() {
    this.leftEncoder.setSelectedSensorPosition(0);
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

  /**
   * We devide the output of getSelectedSensorVelocity from tick per 0.1 second to
   * meter per second
   */
  public double getRightVelocity() {
    return this.rightEncoder.getSelectedSensorVelocity() / (TICKS_PER_METER * 0.1);
  }

  /**
   * We devide the output of getSelectedSensorVelocity from tick per 0.1 second to
   * meter per second
   */
  public double getLeftVelocity() {
    return this.leftEncoder.getSelectedSensorVelocity() / (TICKS_PER_METER * 0.1);
  }

  public double getAverageVelocity() {
    return (getRightVelocity() + getLeftVelocity()) / 2;
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

    this.leftAcceleration = (getLeftVelocity() - this.prevLeftVelocity) / (currentTime - prevTime);
    this.rightAcceleration = (getRightVelocity() - this.prevRightVelocity) / (currentTime - prevTime);

    this.prevTime = currentTime;
    this.prevLeftVelocity = getLeftVelocity();
    this.prevRightVelocity = getRightVelocity();
  }

  private void setSparksSettings(CANSparkMax front, CANSparkMax middle, CANSparkMax rear){
    // Ramp limit to go from 0 to full power, in seconds
    front.setOpenLoopRampRate(RAMP_LIMIT);
    middle.setOpenLoopRampRate(RAMP_LIMIT);
    rear.setOpenLoopRampRate(RAMP_LIMIT);

    // Motor mode (break/coast) 
    front.setIdleMode(IdleMode.kCoast);
    middle.setIdleMode(IdleMode.kCoast);
    rear.setIdleMode(IdleMode.kCoast);

    // Current limit
    front.setSmartCurrentLimit(40);
    middle.setSmartCurrentLimit(40);
    rear.setSmartCurrentLimit(40);
    
    // Saves the settings for each Spark Max
    front.burnFlash();
    middle.burnFlash();
    rear.burnFlash();
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveArcade(() -> Robot.oi.driverXbox.getX(Hand.kLeft), () -> Robot.oi.driverXbox.getY(Hand.kLeft)));
  }
}
