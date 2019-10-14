package frc.robot.pidsources;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.Robot;


/**
 * Returns the encoder pid source from the avarage distance that the robot drove,
 * from both the encoders on each side.
 */

public class DriveForwardPidSource implements PIDSource {
    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
    }

    @Override
    public double pidGet() {
        return Robot.drivetrain.getAverageDistance();
    }
}