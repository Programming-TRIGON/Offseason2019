
package frc.robot.sensors;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * this class extends the PigeonIMU class for convenient API that implements Gyro and PIDSource.
 */
public class Pigeon extends PigeonIMU implements Gyro, PIDSource {
  private double[] yawPitchRoll;
  private double[] accelerometerAngles;
  private GeneralStatus generalStatus;
  private PIDSourceType pidSource;

  public Pigeon(int deviceNumber) {
    super(deviceNumber);
    yawPitchRoll = new double[3];
    accelerometerAngles = new double[3];
    generalStatus = new GeneralStatus();
  }

  public Pigeon(TalonSRX talonSRX) {
    super(talonSRX);
    yawPitchRoll = new double[3];
    accelerometerAngles = new double[3];
    generalStatus = new GeneralStatus();
  }

  public double getYaw() {
    getYawPitchRoll(yawPitchRoll);
    return yawPitchRoll[0];
  }

  /**
   * @return whether the gyro finished calibrating and is ready to use
   */
  public boolean isReady() {
    getGeneralStatus(generalStatus);
    return generalStatus.state.equals(PigeonState.Ready);
  }


  @Override
  public void calibrate() {
    enterCalibrationMode(CalibrationMode.BootTareGyroAccel);
  }

  @Override
  public void reset() {
    setYaw(0);
  }

  @Override
  public double getAngle() {
    return getYaw();
  }

  @Override
  public double getRate() {
    getAccelerometerAngles(accelerometerAngles);
    return accelerometerAngles[2];
  }

  @Override
  public void setPIDSourceType(PIDSourceType pidSource) {
    this.pidSource = pidSource;
  }

  @Override
  public PIDSourceType getPIDSourceType() {
    return pidSource;
  }

  @Override
  public double pidGet() {
    switch (pidSource) {
      case kRate:
        return this.getRate();
      case kDisplacement:
        return this.getAngle();
      default:
        return 0;
    }
  }

  @Override
  public void close() throws Exception {
    DestroyObject();
  }

  @Override
  public void free() {
    DestroyObject();
  }

}
