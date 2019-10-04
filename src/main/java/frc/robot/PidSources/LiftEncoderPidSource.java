package frc.robot.PidSources;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.Robot;

public class LiftEncoderPidSource implements PIDSource {
    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }
    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        
    }
    @Override
    public double pidGet() {
        return Robot.lift.getHeight();
    }
}
