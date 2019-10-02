
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotComponents;



public class Tilt extends Subsystem {
  private final Encoder encoder; 
  private final WPI_TalonSRX talon; 
  public Tilt(){
    this(RobotComponents.Tilt.MOTOR,RobotComponents.Tilt.ENCODER);
  }
  public Tilt(WPI_TalonSRX talon, Encoder encoder){
    this.encoder = encoder;
    //TODO: set distance per pulse.
    encoder.setDistancePerPulse(1.0);
    this.talon = talon;
  }
  public double getHeight(){
    return encoder.get();
  }
  public void moveTilt(double power){
    talon.set(ControlMode.PercentOutput, power);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
