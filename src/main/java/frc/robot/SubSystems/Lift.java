package frc.robot.SubSystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;
/** This is the subsystem of the lift */
public class Lift extends Subsystem {
  private SpeedControllerGroup motors = RobotComponents.Lift.LIFT_MOTORS;
  private Encoder encoder = RobotComponents.Lift.LIFT_ENCODER;

public void setMotors(double speed){
  motors.set(speed);
}
public double getEncoder(){
  return this.encoder.getDistance();
}
public void setEncoderDPP(double dpp){
  this.encoder.setDistancePerPulse(dpp);
}
  @Override
  public void initDefaultCommand() {
  }
}
