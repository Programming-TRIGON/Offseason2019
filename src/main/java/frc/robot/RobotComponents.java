package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

/** This class sets what all of the robot instance components are */
public class RobotComponents {
    public static class Lift {
        public static final TalonSRX LIFT_MOTOR_FRONT = new TalonSRX(RobotMap.CAN.LIFT_MOTOR_FRONT);
        public static final TalonSRX LIFT_MOTOR_REAR = new TalonSRX(RobotMap.CAN.LIFT_MOTOR_REAR);
        public static final DigitalInput LIFT_SWITCH_TOP = new DigitalInput(RobotMap.DIO.LIFT_SWITCH_TOP);
        public static final DigitalInput LIFT_SWITCH_BOTTOM = new DigitalInput(RobotMap.DIO.LIFT_SWITCH_BOTTOM);
    }

}
