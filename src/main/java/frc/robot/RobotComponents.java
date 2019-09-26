package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/** This class sets what all of the robot instance components are */
public class RobotComponents {

    /*
     * Add your class components here, and remove comment. example:
     * 
     * public static class Drivetrain { public static final WPI_TalonSRX
     * REAR_RIGHT_MOTOR = new TalonSRX(RobotMap.CAN.REAR_RIGHT_MOTOR); etc. }
     */
    public static class Lift {
        public static final WPI_TalonSRX LIFT_MOTOR1 = new WPI_TalonSRX(RobotMap.CAN.LIFT_MOTER1);
        public static final WPI_TalonSRX LIFT_MOTOR2 = new WPI_TalonSRX(RobotMap.CAN.LIFT_MOTOR2);
        public static final SpeedControllerGroup LIFT_MOTORS = new SpeedControllerGroup(Lift.LIFT_MOTOR1,
                Lift.LIFT_MOTOR2);
        public static final Encoder LIFT_ENCODER = new Encoder(RobotMap.DIO.LIFT_ENCODER_CHANNEL_A,
                RobotMap.DIO.LIFT_ENCODER_CHANNEL_B);
    }

}
