
package frc.robot.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Add your docs here.
 */
public class ToggleButton extends JoystickButton {
  public ToggleButton(GenericHID joystick, int buttonNumber) {
    super(joystick, buttonNumber);
  }

  public void toggleCommands(Command command1,Command command2){
    new ButtonScheduler() {
      private boolean m_pressedLast = get();

      @Override
      public void execute() {
        boolean pressed = get();

        if (!m_pressedLast && pressed) {
          if (command1.isRunning()) {
            command1.cancel();
          }
          command2.start();
          } else {
            if (command2.isRunning()) {
              command2.cancel();
            }
          command1.start();
          }
        m_pressedLast = pressed;
      }
    }.start();
}}
