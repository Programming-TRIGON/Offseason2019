package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/** This class sets what all of the robot instance components are */
public class RobotComponents {
    
    public static class HatchHolder {
        public static final DoubleSolenoid LOCK_SOLENOID = new DoubleSolenoid(RobotMap.PCM.HATCH_HOLDER_LOCK_SOLENOID_FORWARD, RobotMap.PCM.HATCH_HOLDER_LOCK_SOLENOID_REVERSE);
        public static final DoubleSolenoid EJECT_SOLENOID = new DoubleSolenoid(RobotMap.PCM.HATCH_HOLDER_EJECT_SOLENOID_FORWARD, RobotMap.PCM.HATCH_HOLDER_EJECT_SOLENOID_REVERSE);        
    }
}
