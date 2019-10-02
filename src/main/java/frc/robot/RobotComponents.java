package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;

/** This class sets what all of the robot instance components are */
public class RobotComponents {
    
    /* Add your class components here, and remove comment. example: 

    public static class Drivetrain {
        public static final TalonSRX REAR_RIGHT_MOTOR = new TalonSRX(RobotMap.CAN.REAR_RIGHT_MOTOR);
        etc.
    }
    */
    public static class Tilt{
        public static final WPI_TalonSRX MOTOR = new WPI_TalonSRX(RobotMap.CAN.TILT_MOTOR); 
        public static final Encoder ENCODER = new Encoder(RobotMap.DIO.ENCODER_A,RobotMap.DIO.ENCODER_B); 
    } 
}
