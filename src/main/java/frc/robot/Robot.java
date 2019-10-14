package frc.robot;

import com.spikes2212.dashboard.DashBoardController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Enums.LiftHeights;
import frc.robot.Enums.Target;
import frc.robot.command_group.ScoreCargo;
import frc.robot.commands.*;
import frc.robot.autonomous.AutonomousCommand;
import frc.robot.motionprofiling.PathCreater;
import frc.robot.subsystems.CargoHolder;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HatchHolder;
import frc.robot.subsystems.Lift;
import frc.robot.testpids.TestPID;
import frc.robot.testpids.TestPIDGyro;
import frc.robot.testpids.TestPIDLift;
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
    oi = new OI();
    pathCreater = new PathCreater();
    limelight = new Limelight();
    dbc = new DashBoardController();

    autonomousChooser = new SendableChooser<Command>();
    autonomousChooser.setDefaultOption("Default Auto", null);
    autonomousChooser.addOption("Right cargo ship", new AutonomousCommand(false));
    autonomousChooser.addOption("Left cargo ship", new AutonomousCommand(true));

    SmartDashboard.putData("Auto mode", autonomousChooser);
    SmartDashboard.putData("CalibrateDistance", new CalibrateDistance(oi.driverXbox::getAButton));
    SmartDashboard.putData("Test PID vision", new TestPID());
    SmartDashboard.putData("test PID Turn", new TestPIDGyro());
    SmartDashboard.putData("test pid Lift", new TestPIDLift());
    SmartDashboard.putData("clearPreferences", Commands.clearPreferences());
    SmartDashboard.putData("scheduler",Scheduler.getInstance());
    MoveLiftWithJoystick data = new MoveLiftWithJoystick(() -> 0.0);
    data.setRunWhenDisabled(true);
    SmartDashboard.putData("move with joystick", data);
    var data1 = new ScoreCargo(LiftHeights.CargoShip, Target.RocketMiddle);
    data1.setRunWhenDisabled(true);
    SmartDashboard.putData("setLift", data1);
    // dbc SmartDashboard values to display
    dbc.addNumber("limelight distance", limelight::getDistance);
    dbc.addNumber("robot angle", drivetrain::getAngle);
    dbc.addNumber("lift height", lift::getHeight);
  }

  @Override
  public void robotPeriodic() {
    dbc.update();

    //if (lift.getBottomSwitch())
    // lift.resetEncoderHeight();
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
