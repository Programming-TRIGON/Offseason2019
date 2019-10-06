package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class RobotComponents {
    
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
