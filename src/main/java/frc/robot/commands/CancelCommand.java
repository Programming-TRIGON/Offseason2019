package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Cancel a Command
 */
public class CancelCommand extends InstantCommand {
  Supplier<Command> command;
  public CancelCommand(Command c) {
    super();
    command = () -> c;
  }

  public CancelCommand(Supplier<Command> c) {
    command = c;
}

  @Override
  protected void initialize() {
    if(command.get() != null)
      command.get().cancel();
  }

}
