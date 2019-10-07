package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RobotComponents {
    
    public static class CargoCollector {
        public static final DoubleSolenoid TILT_SOLENOID = new DoubleSolenoid(RobotMap.PCM.TILT_SOLENOID_FORWARD, RobotMap.PCM.TILT_SOLENOID_REVERSE);
        public static final WPI_TalonSRX HOLDER_MOTOR = new WPI_TalonSRX(RobotMap.CAN.CARGO_HOLDER_MOTOR);
        public static final DigitalInput HOLDER_SWITCH = new DigitalInput(RobotMap.DIO.HOLDER_SWITCH);
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
        public static final WPI_TalonSRX LEFT_ENCODER_PLACEHOLDER = new WPI_TalonSRX(
                RobotMap.CAN.PLACEHOLDER_TALON_LEFT);
        public static final WPI_TalonSRX RIGHT_ENCODER_PLACEHOLDER = new WPI_TalonSRX(
                RobotMap.CAN.PLACEHOLDER_TALON_RIGHT);

        public static final ADXRS450_Gyro GYRO = new ADXRS450_Gyro();
    }
    
    public static class HatchHolder {
        public static final DoubleSolenoid LOCK_SOLENOID = new DoubleSolenoid(RobotMap.PCM.HATCH_HOLDER_LOCK_SOLENOID_FORWARD, RobotMap.PCM.HATCH_HOLDER_LOCK_SOLENOID_REVERSE);
        public static final DoubleSolenoid EJECT_SOLENOID = new DoubleSolenoid(RobotMap.PCM.HATCH_HOLDER_EJECT_SOLENOID_FORWARD, RobotMap.PCM.HATCH_HOLDER_EJECT_SOLENOID_REVERSE);        
    }
    
    public static class Lift {
        public static final TalonSRX LIFT_MOTOR_FRONT = new TalonSRX(RobotMap.CAN.LIFT_MOTOR_FRONT);
        public static final TalonSRX LIFT_MOTOR_REAR = new TalonSRX(RobotMap.CAN.LIFT_MOTOR_REAR);
        public static final DigitalInput LIFT_SWITCH_TOP = new DigitalInput(RobotMap.DIO.LIFT_SWITCH_TOP);
        public static final DigitalInput LIFT_SWITCH_BOTTOM = new DigitalInput(RobotMap.DIO.LIFT_SWITCH_BOTTOM);
    }
}
