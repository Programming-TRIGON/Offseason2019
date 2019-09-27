package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.SubSystems.Lift;

public class Robot extends TimedRobot {
  public static OI oi;
  public static Lift lift;
  // Add your Subsystem here, and remove comment. example:
  // public static Drivetrain drivetrain;

  private Command autonomousCommand;
  private SendableChooser<Command> autonomousChooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    // Also add your Subsystem here, and remove comment. example:
    // drivetrain = new Drivetrain();

    oi = new OI();
    lift = new Lift();

    // autonomousChooser.setDefaultOption("Default Auto", new ExampleCommand());
    // autonomousChooser.addOption("Auto", new AutoCommand());
    SmartDashboard.putData("Auto mode", autonomousChooser);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    autonomousCommand = autonomousChooser.getSelected();
    if (autonomousCommand != null) {
      autonomousCommand.start();
    }
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
