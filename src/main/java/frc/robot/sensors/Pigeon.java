
package frc.robot.sensors;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Pigeon extends PigeonIMU implements Gyro {
    private double[] yawPitchRoll;
    private double[] accelerometerAngles;
    private GeneralStatus generalStatus;

    public Pigeon(int deviceNumber) {
        super(deviceNumber);
        yawPitchRoll = new double[3];
        accelerometerAngles = new double[3];
        generalStatus = new GeneralStatus();
    }
    public Pigeon(TalonSRX talonSRX){
        super(talonSRX);
        yawPitchRoll = new double[3];
        accelerometerAngles = new double[3];
        generalStatus = new GeneralStatus();
    }
    public double getYaw(){
        getYawPitchRoll(yawPitchRoll);
        return yawPitchRoll[0];
    }
    /**
     * @return whether the gyro finished calibrating and is ready to use
     */
    public boolean isReady(){
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
        // TODO Replace with accelerometer
        return 0;
    }

    @Override
    public void free() {
        DestroyObject();
    }
    
    @Override
    public void close() throws Exception {
      DestroyObject();
    }

}
