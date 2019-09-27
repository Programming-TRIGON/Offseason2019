package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

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
        public static final WPI_TalonSRX LEFT_ENCODER_PLACEHOLDER = new WPI_TalonSRX(
                RobotMap.CAN.PLACEHOLDER_TALON_LEFT);
        public static final WPI_TalonSRX RIGHT_ENCODER_PLACEHOLDER = new WPI_TalonSRX(
                RobotMap.CAN.PLACEHOLDER_TALON_RIGHT);

        public static final ADXRS450_Gyro GYRO = new ADXRS450_Gyro();
    }

}
