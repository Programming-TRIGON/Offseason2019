package frc.robot;

import com.spikes2212.dashboard.DashBoardController;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.command_group.CollectHatchFromFeeder;
import frc.robot.commands.*;
import frc.robot.motionprofiling.PathCreater;
import frc.robot.subsystems.CargoHolder;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HatchHolder;
import frc.robot.subsystems.Lift;
import frc.robot.testpids.TestPIDVision;
import frc.robot.testpids.TestPIDGyro;
import frc.robot.testpids.TestPIDLift;
import frc.robot.utils.Limelight;
import frc.robot.utils.Limelight.CamMode;

public class Robot extends TimedRobot {
  public static OI oi;
  public static PathCreater pathCreater;
  public static CargoHolder cargoHolder;
  public static Drivetrain drivetrain;
  public static Lift lift;
  public static HatchHolder hatchHolder;
  public static Limelight limelight;
  private Command autonomousCommand;
  private SendableChooser<Command> autonomousChooser;
  private DashBoardController dbc;

  @Override
  public void robotInit() {
    // Compressor:
    RobotComponents.compressor.start();
    // Subsystems:
    cargoHolder = new CargoHolder();
    drivetrain = new Drivetrain();
    lift = new Lift();
    hatchHolder = new HatchHolder();
    // Utils:
    oi = new OI(true);
    pathCreater = new PathCreater();
    limelight = new Limelight();
    dbc = new DashBoardController();

    autonomousChooser = new SendableChooser<Command>();
    
    //autonomousChooser.setDefaultOption("Default Auto", null);
    //autonomousChooser.addOption("Right cargo ship", new AutonomousCommand(false));
    //autonomousChooser.addOption("Left cargo ship", new AutonomousCommand(true));

    SmartDashboard.putData("Auto mode", autonomousChooser);
    SmartDashboard.putData("CalibrateDistance", new CalibrateDistance(oi.driverXbox::getAButton));
    SmartDashboard.putData("Test PID vision", new TestPIDVision());
    SmartDashboard.putData("test PID Turn", new TestPIDGyro());
    SmartDashboard.putData("test pid Lift", new TestPIDLift());
    SmartDashboard.putData("clearPreferences", Commands.setRunWhenDisabled(Preferences.getInstance()::removeAll));
    SmartDashboard.putData("limelight toggle", Commands.setRunWhenDisabled(limelight::toggleLedMode));
    SmartDashboard.putData("Auto hatch feeder collection", new CollectHatchFromFeeder());
    SmartDashboard.putData("Compressor stop", Commands.stopCompressor());
    SmartDashboard.putData("Compressor start", Commands.startCompressor());

    // dbc SmartDashboard values to display
    dbc.addNumber("limelight distance", limelight::getDistance);
    dbc.addNumber("robot angle", drivetrain::getAngle);
    dbc.addNumber("lift height", lift::getHeight);
    dbc.addBoolean("Is Cargo collected ", cargoHolder::isCargoCollected);

    limelight.setCamMode(CamMode.vision);
  }

  @Override
  public void robotPeriodic() {
    dbc.update();
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
    // autonomousCommand = autonomousChooser.getSelected();
    // if (autonomousCommand != null) {
    //   autonomousCommand.start();
    // }
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // if (autonomousCommand != null) {
    //   autonomousCommand.cancel();
    // }
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
