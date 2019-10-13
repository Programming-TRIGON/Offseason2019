package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.motionprofiling.PathCreater;
import frc.robot.subsystems.CargoHolder;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HatchHolder;
import frc.robot.subsystems.Lift;
import frc.robot.utils.Limelight;

public class Robot extends TimedRobot {
  public static OI oi;
  public static PathCreater pathCreater;
  public static CargoHolder cargoHolder;
  public static Drivetrain drivetrain;
  public static Lift lift;
  public static HatchHolder hatchHolder;
  public static Limelight limelight;
  private Command autonomousCommand;
  private SendableChooser<Command> autonomousChooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    //compressor:
    RobotComponents.compressor.start();
    // Utils:
    oi = new OI();
    pathCreater = new PathCreater();
    limelight = new Limelight();
    // Subsystems:
    cargoHolder = new CargoHolder();
    drivetrain = new Drivetrain();
    lift = new Lift();
    hatchHolder = new HatchHolder();
    // autonomousChooser.setDefaultOption("Default Auto", new ExampleCommand());
    // autonomousChooser.addOption("Auto", new AutoCommand());
    SmartDashboard.putData("Auto mode", autonomousChooser);
  }

  @Override
  public void robotPeriodic() {
    if (lift.getBottomSwitch())
      lift.resetEncoderHeight();
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
