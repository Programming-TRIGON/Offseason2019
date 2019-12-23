package frc.robot.subsystems;

import java.util.function.Supplier;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.spikes2212.dashboard.ConstantHandler;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.RobotComponents;
import frc.robot.RobotConstants;
import frc.robot.RobotConstants.MotionProfiling;
import frc.robot.RobotConstants.RobotDimensions;
import frc.robot.commands.DriveArcade;
import frc.robot.sensors.Pigeon;
import wpilibj.geometry.Pose2d;
import wpilibj.geometry.Rotation2d;
import wpilibj.kinematics.DifferentialDriveKinematics;
import wpilibj.kinematics.DifferentialDriveOdometry;
import wpilibj.kinematics.DifferentialDriveWheelSpeeds;

/** This is the susbsystem for the drivetrain of the robot */
public class Drivetrain extends Subsystem {
  private static final double RAMP_LIMIT = 0; // In seconds, to full speed
  private final DifferentialDriveOdometry odometry;
  private SpeedControllerGroup leftDriveGroup, rightDriveGroup;
  private WPI_TalonSRX rightEncoder, leftEncoder;
  private DifferentialDrive drivetrain;
  private Pigeon gyro;
  private double prevTime = 0, leftAcceleration = 0, rightAcceleration = 0, currentTime = 0, prevLeftVelocity = 0,
      prevRightVelocity = 0;
  private double TICKS_PER_METER = RobotConstants.Sensors.DRIVETRAIN_ENCODERS_DISTANCE_PER_TICKS;
  private Supplier<Double> angleOffset;
  private boolean isXLock, canDrive = true;
  private DifferentialDriveKinematics kinematics;


  public Drivetrain() {
    // Settings for each side of the robot
    setSparksSettings(RobotComponents.Drivetrain.LEFT_FRONT_MOTOR, RobotComponents.Drivetrain.LEFT_MIDDLE_MOTOR,
        RobotComponents.Drivetrain.LEFT_REAR_MOTOR);
    setSparksSettings(RobotComponents.Drivetrain.RIGHT_FRONT_MOTOR, RobotComponents.Drivetrain.RIGHT_MIDDLE_MOTOR,
        RobotComponents.Drivetrain.RIGHT_REAR_MOTOR);

    this.leftDriveGroup = new SpeedControllerGroup(RobotComponents.Drivetrain.LEFT_FRONT_MOTOR,
        RobotComponents.Drivetrain.LEFT_MIDDLE_MOTOR, RobotComponents.Drivetrain.LEFT_REAR_MOTOR);
    this.rightDriveGroup = new SpeedControllerGroup(RobotComponents.Drivetrain.RIGHT_FRONT_MOTOR,
        RobotComponents.Drivetrain.RIGHT_MIDDLE_MOTOR, RobotComponents.Drivetrain.RIGHT_REAR_MOTOR);

    this.drivetrain = new DifferentialDrive(this.leftDriveGroup, this.rightDriveGroup);
    this.gyro = RobotComponents.Drivetrain.GYRO;
    gyro.calibrate();

    drivetrain.setSafetyEnabled(false);

    this.rightEncoder = RobotComponents.Drivetrain.ENCODER_RIGHT;
    this.leftEncoder = RobotComponents.CargoCollector.HOLDER_MOTOR;
    drivetrain.setDeadband(0.0);
    angleOffset = ConstantHandler.addConstantDouble("angle offset", 0);
    kinematics = new DifferentialDriveKinematics(MotionProfiling.WHEEL_BASE_WIDTH);
    odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getAngle()));
  }

  public void arcadeDrive(double x, double y) {
    this.drivetrain.arcadeDrive(y, x);
  }

  public void tankDrive(double rightSpeed, double leftSpeed) {
    this.drivetrain.tankDrive(-leftSpeed, -rightSpeed, false);
  }

  public void curvatureDrive(double x, double y, boolean quickTurn) {
    this.drivetrain.curvatureDrive(y, x, quickTurn);
  }

  public double getAngle() {
    return Math.IEEEremainder(gyro.getAngle() + angleOffset.get(), 360);
  }

  public void resetGyro() {
    this.gyro.reset();
  }

  public void calibrateGyro() {
    this.gyro.calibrate();
    resetGyro();
  }

  public int getLeftTicks() {
    return -this.leftEncoder.getSelectedSensorPosition();
  }

  public int getRightTicks() {
    return this.rightEncoder.getSelectedSensorPosition();
  }

  public void resetEncoders() {
    this.leftEncoder.setSelectedSensorPosition(0);
    this.rightEncoder.setSelectedSensorPosition(0);
  }

  public double getRightDistance() {
    return getRightTicks() / RobotDimensions.RIGHT_TICKS_PER_METER;
  }

  public double getLeftDistance() {
    return getLeftTicks() / RobotDimensions.LEFT_TICKS_PER_METER;
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
    return -this.leftEncoder.getSelectedSensorVelocity() / (TICKS_PER_METER * 0.1);
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
    odometry.update(Rotation2d.fromDegrees(getAngle()), getLeftDistance(), getRightDistance());
  }

  private void setSparksSettings(CANSparkMax front, CANSparkMax middle, CANSparkMax rear) {
    // Ramp limit to go from 0 to full power, in seconds
    front.setOpenLoopRampRate(RAMP_LIMIT);
    middle.setOpenLoopRampRate(RAMP_LIMIT);
    rear.setOpenLoopRampRate(RAMP_LIMIT);

    // Motor mode (break/coast)
    front.setIdleMode(IdleMode.kCoast);
    middle.setIdleMode(IdleMode.kCoast);
    rear.setIdleMode(IdleMode.kCoast);

    // Current limit
    front.setSmartCurrentLimit(80);
    middle.setSmartCurrentLimit(80);
    rear.setSmartCurrentLimit(80);

    // Saves the settings for each Spark Max
    front.burnFlash();
    middle.burnFlash();
    rear.burnFlash();
  }

  public boolean getIsXLock() {
    return isXLock;
  }

  public void setXLock(boolean isXLock) {
    this.isXLock = isXLock;
  }

  public boolean getCanDrive() {
    return canDrive;
  }

  public void setCanDrive(boolean canDrive) {
    this.canDrive = canDrive;
  }

  public void toggleCanDrive() {
    canDrive = !canDrive;
  }

  public DifferentialDriveKinematics getKinematics() {
    return kinematics;
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity());
  }

  public void resetOdometry(){
    resetOdometry(new Pose2d(0,0, Rotation2d.fromDegrees(0)));
  }
  public void resetOdometry(Pose2d pose){
    resetEncoders();
    Rotation2d gyroAngle = Rotation2d.fromDegrees(getAngle());
    odometry.resetPosition(pose, gyroAngle);
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public void voltageTankDrive(double left, double right) {
    tankDrive(left / RobotController.getBatteryVoltage(), right / RobotController.getBatteryVoltage());
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveArcade(() -> Robot.oi.driverXbox.getX(Hand.kLeft),
            () -> Robot.oi.driverXbox.getTriggerAxis(Hand.kLeft), () -> Robot.oi.driverXbox.getTriggerAxis(Hand.kRight)));
  }
}
