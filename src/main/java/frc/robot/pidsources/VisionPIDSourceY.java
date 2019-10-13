package frc.robot.pidsources;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.Robot;

/**
 * pid source for the x axis using vision
 */
public class VisionPIDSourceY implements PIDSource {

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
    }

    @Override
    public double pidGet() {
        double distance = Robot.limelight.getDistance();
        if (distance == -1)
            return 95;
        return distance;
    }
}
