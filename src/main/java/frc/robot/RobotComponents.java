package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

/** This class sets what all of the robot instance components are */
public class RobotComponents {
    
    public static class CargoCollector {
        public static final WPI_TalonSRX HOLDER_MOTOR = new WPI_TalonSRX(RobotMap.CAN.CARGO_HOLDER_MOTOR);
        public static final WPI_TalonSRX COLLECTOR_MOTOR = new WPI_TalonSRX(RobotMap.CAN.CARGO_COLLECTOR_MOTOR);
        public static final DigitalInput HOLDER_SWITCH = new DigitalInput(RobotMap.DIO.HOLDER_SWITCH);
    }
}
