package frc.robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**This class gives the error the gyro has for PID*/
public class GyroPIDSource implements PIDSource {
    
        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }
    
        @Override
        public double pidGet() {
            return Robot.drivetrain.getAngle();
        }
    
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
    
        }
}
