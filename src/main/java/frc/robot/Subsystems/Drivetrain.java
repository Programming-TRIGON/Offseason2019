package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;

/** This is the susbsystem for the drivetrain of the robot */
public class Drivetrain extends Subsystem {
  private SpeedControllerGroup leftDriveMotors, rightDriveMotors;
  private Encoder leftEncoder, rightEncoder;
  
  public Drivetrain(){
    this.leftDriveMotors = new SpeedControllerGroup(RobotComponents.Drivetrain.LEFT_FRONT_MOTOR, RobotComponents.Drivetrain.LEFT_MIDDLE_MOTOR, RobotComponents.Drivetrain.LEFT_REAR_MOTOR);
    this.rightDriveMotors = new SpeedControllerGroup(RobotComponents.Drivetrain.RIGHT_FRONT_MOTOR, RobotComponents.Drivetrain.RIGHT_MIDDLE_MOTOR, RobotComponents.Drivetrain.RIGHT_REAR_MOTOR);
    this.leftEncoder = RobotComponents.Drivetrain.ENCODER_LEFT;
    this.leftEncoder = RobotComponents.Drivetrain.ENCODER_RIGHT;
  }

  


  @Override
  public void initDefaultCommand() {
  }
}
