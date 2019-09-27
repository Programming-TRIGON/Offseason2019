package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;

/** This class sets what all of the robot instance components are */
public class RobotComponents {

    public static class Drivetrain {
        public static final CANSparkMax LEFT_REAR_MOTOR = new CANSparkMax(RobotMap.CAN.DRIVETRAIN_LEFT_REAR_MOTOR,
                MotorType.kBrushless);
        public static final CANSparkMax LEFT_MIDDLE_MOTOR = new CANSparkMax(RobotMap.CAN.DRIVETRAIN_LEFT_MIDDLE_MOTOR,
                MotorType.kBrushless);
        public static final CANSparkMax LEFT_FRONT_MOTOR = new CANSparkMax(RobotMap.CAN.DRIVETRAIN_LEFT_FRONT_MOTOR,
                MotorType.kBrushless);
        public static final CANSparkMax RIGHT_REAR_MOTOR = new CANSparkMax(RobotMap.CAN.DRIVETRAIN_RIGHT_REAR_MOTOR,
                MotorType.kBrushless);
        public static final CANSparkMax RIGHT_MIDDLE_MOTOR = new CANSparkMax(RobotMap.CAN.DRIVETRAIN_RIGHT_MIDDLE_MOTOR,
                MotorType.kBrushless);
        public static final CANSparkMax RIGHT_FRONT_MOTOR = new CANSparkMax(RobotMap.CAN.DRIVETRAIN_RIGHT_FRONT_MOTOR,
                MotorType.kBrushless);

        public static final Encoder ENCODER_LEFT = new Encoder(RobotMap.DIO.DRIVETRAIN_ENCODER_LEFT_CHANNEL_A,
                RobotMap.DIO.DRIVETRAIN_ENCODER_LEFT_CHANNEL_B);
        public static final Encoder ENCODER_RIGHT = new Encoder(RobotMap.DIO.DRIVETRAIN_ENCODER_RIGHT_CHANNEL_A,
                RobotMap.DIO.DRIVETRAIN_ENCODER_RIGHT_CHANNEL_B);
    }

}
