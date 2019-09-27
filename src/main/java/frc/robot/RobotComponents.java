package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/** This class sets what all of the robot instance components are */
public class RobotComponents {
    
    public static class CargoCollector {
        public static final WPI_TalonSRX HOLDER_MOTOR = new WPI_TalonSRX(RobotMap.CAN.CARGO_COLLECTOR_HOLDER_MOTOR);
    }
}
