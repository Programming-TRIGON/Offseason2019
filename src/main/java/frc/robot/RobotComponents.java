package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/** This class sets what all of the robot instance components are */
public class RobotComponents {
    public static class Lift {
        public static final WPI_TalonSRX LIFT_MOTOR_FRONT = new WPI_TalonSRX(RobotMap.CAN.LIFT_MOTOR_FRONT);
        public static final WPI_TalonSRX LIFT_MOTOR_REAR = new WPI_TalonSRX(RobotMap.CAN.LIFT_MOTOR_REAR);
        public static final SpeedControllerGroup LIFT_MOTORS = new SpeedControllerGroup(Lift.LIFT_MOTOR_FRONT,
                Lift.LIFT_MOTOR_REAR);
        public static final DigitalInput LIFT_SWITCH_TOP = new DigitalInput(RobotMap.DIO.LIFT_SWITCH_TOP);
        public static final DigitalInput LIFT_SWITCH_BOTTOM = new DigitalInput(RobotMap.DIO.LIFT_SWITCH_BOTTOM);
    }

}
