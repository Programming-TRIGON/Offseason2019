
package frc.robot.command_groups;

import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.MoveTiltLinear;
import frc.robot.commands.MoveTiltPID;

public class MoveTilt extends CommandGroup {
  
  public MoveTilt(double setpoint) {
    addSequential(new MoveTiltLinear(setpoint));
    addSequential(new MoveTiltPID(setpoint));
  }
  public MoveTilt(double setpoint, PIDSettings settings){
    addSequential(new MoveTiltLinear(setpoint));
    addSequential(new MoveTiltPID(setpoint,settings));
  }
}
