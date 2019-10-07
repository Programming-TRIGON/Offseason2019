package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CargoHolder;
import frc.robot.motionprofiling.PathCreater;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HatchHolder;
import frc.robot.subsystems.Lift;

public class Robot extends TimedRobot {
  public static OI oi;
  public static CargoHolder cargoHolder;
  public static PathCreater pathCreater;
  public static Drivetrain drivetrain;
  public static Lift lift;
  public static HatchHolder hatchHolder; 
  private Command autonomousCommand;
  private SendableChooser<Command> autonomousChooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    cargoHolder = new CargoHolder(); 
    oi = new OI();
    pathCreater = new PathCreater();
    drivetrain = new Drivetrain();
    lift = new Lift();
    hatchHolder = new HatchHolder();
    
    // autonomousChooser.setDefaultOption("Default Auto", new ExampleCommand());
    // autonomousChooser.addOption("Auto", new AutoCommand());
    SmartDashboard.putData("Auto mode", autonomousChooser);
  }

  @Override
  public void robotPeriodic() {
    //System.out.println(Robot.oi.getJoystickDirection());
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
