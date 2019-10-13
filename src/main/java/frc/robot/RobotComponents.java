package frc.robot;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;

public class RobotComponents {

    public static Compressor compressor = new Compressor(RobotMap.PCM.PCM_PORT);

    public static class CargoCollector {
        public static final WPI_TalonSRX HOLDER_MOTOR = new WPI_TalonSRX(RobotMap.CAN.CARGO_HOLDER_MOTOR);
    }
     
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

        public static final ADIS16448_IMU GYRO = new ADIS16448_IMU();

        public static final WPI_TalonSRX ENCODER_RIGHT = new WPI_TalonSRX(RobotMap.CAN.ENCODER_RIGHT);
    }
    
    public static class HatchHolder {
        public static final DoubleSolenoid LOCK_SOLENOID = null; //new DoubleSolenoid(RobotMap.PCM.HATCH_HOLDER_LOCK_SOLENOID_FORWARD, RobotMap.PCM.HATCH_HOLDER_LOCK_SOLENOID_REVERSE);
        public static final DoubleSolenoid EJECT_SOLENOID = null; //new DoubleSolenoid(RobotMap.PCM.HATCH_HOLDER_EJECT_SOLENOID_FORWARD, RobotMap.PCM.HATCH_HOLDER_EJECT_SOLENOID_REVERSE);        
    }
    
    public static class Lift {
        public static final Encoder encoder = new Encoder(RobotMap.DIO.LIFT_ENCODER_CHANNEL_A, RobotMap.DIO.LIFT_ENCODER_CHANNEL_B);
        public static final WPI_TalonSRX LIFT_MOTOR_LEFT = new WPI_TalonSRX(RobotMap.CAN.LIFT_MOTOR_LEFT);
        public static final WPI_TalonSRX LIFT_MOTOR_RIGHT = new WPI_TalonSRX(RobotMap.CAN.LIFT_MOTOR_RIGHT);
        public static final DigitalInput LIFT_SWITCH_TOP = new DigitalInput(RobotMap.DIO.LIFT_SWITCH_TOP);
        public static final DigitalInput LIFT_SWITCH_BOTTOM = new DigitalInput(RobotMap.DIO.LIFT_SWITCH_BOTTOM);
    }
}
