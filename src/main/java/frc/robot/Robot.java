package frc.robot;

import com.spikes2212.dashboard.DashBoardController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Enums.RamsetePath;
import frc.robot.autonomous.SideAutonomous;
import frc.robot.command_group.CollectHatchFromFeeder;
import frc.robot.command_group.PutHatch;
import frc.robot.commands.*;
import frc.robot.motionprofiling.RamseteFollowPath;
import frc.robot.subsystems.CargoHolder;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HatchHolder;
import frc.robot.subsystems.Lift;
import frc.robot.testpids.TestPIDVision;
import frc.robot.testpids.TestPIDGyro;
import frc.robot.testpids.TestPIDMotionProfiling;
import frc.robot.utils.Limelight;
import frc.robot.utils.Limelight.CamMode;
import frc.robot.utils.Limelight.LedMode;

public class Robot extends TimedRobot {
  public static OI oi;
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
    RobotComponents.compressor.stop();
    // Subsystems:
    cargoHolder = new CargoHolder();
    drivetrain = new Drivetrain();
    lift = new Lift();
    hatchHolder = new HatchHolder();
    // Utils:
    oi = new OI(true);
    limelight = new Limelight();
    dbc = new DashBoardController();

    autonomousChooser = new SendableChooser<Command>();
    
    autonomousChooser.setDefaultOption("Default Auto", null);
    autonomousChooser.addOption("Right side auto", new SideAutonomous(false));
    autonomousChooser.addOption("Left side auto", new SideAutonomous(true));
    autonomousChooser.addOption("Calibrate Feedforward", new CalibrateFeedForward());

    SmartDashboard.putData("Auto mode", autonomousChooser);
    SmartDashboard.putData("Calibrate Vision Distance", new CalibrateDistance(oi.driverXbox::getAButton));
    SmartDashboard.putData("Test PID vision", new TestPIDVision());
    SmartDashboard.putData("test PID Turn", new TestPIDGyro());
    SmartDashboard.putData("test PID motion profiling", new TestPIDMotionProfiling());
    SmartDashboard.putData("clearPreferences", Commands.setRunWhenDisabled(Preferences.getInstance()::removeAll));
    SmartDashboard.putData("limelight toggle", Commands.setRunWhenDisabled(limelight::toggleLedMode));
    SmartDashboard.putData("Auto hatch feeder collection", new CollectHatchFromFeeder());
    SmartDashboard.putData("Auto put hatch", new PutHatch(true));
    SmartDashboard.putData("Compressor stop", Commands.stopCompressor());
    SmartDashboard.putData("Compressor start", Commands.startCompressor());
    SmartDashboard.putData("Reset encoders", Commands.resetEncoders());
    SmartDashboard.putData("Calibrate gyro", Commands.calibrateGyro());
    SmartDashboard.putData("reset gyro", Commands.resetGyro());
    SmartDashboard.putData("Side Auto", new SideAutonomous(true));
    RamseteFollowPath ramseteFollowPath = new RamseteFollowPath(RamsetePath.BACK_FROM_ROCKET);
    ramseteFollowPath.enableTuning();
    SmartDashboard.putData("test Ramsete", ramseteFollowPath);
    SmartDashboard.putData("move lift", new MoveLiftWithJoystick(()->oi.driverXbox.getY(Hand.kLeft)));
    SmartDashboard.putData("reset height", Commands.resetHeight());
    SmartDashboard.putData("reset odometry", Commands.setRunWhenDisabled(drivetrain::resetOdometry));

    // dbc SmartDashboard values to display
    dbc.addNumber("Limelight distance", limelight::getDistance);
    dbc.addNumber("Robot angle", drivetrain::getAngle);
    dbc.addNumber("Lift height", lift::getHeight);
    dbc.addNumber("Right encoder ticks", drivetrain::getRightTicks);
    dbc.addNumber("Left encoder ticks", drivetrain::getLeftTicks);
    dbc.addNumber("Right velocity", drivetrain::getRightVelocity);
    dbc.addNumber("Left velocity", drivetrain::getLeftVelocity);
    dbc.addNumber("Right acceleration", drivetrain::getRightAcceleration);
    dbc.addNumber("Left acceleration", drivetrain::getLeftAcceleration);
    dbc.addNumber("Right distance", drivetrain::getRightDistance);
    dbc.addNumber("Left distance", drivetrain::getLeftDistance);
    dbc.addBoolean("Is Cargo collected ", cargoHolder::isCargoCollected);
    dbc.addNumber("Target Ts", limelight::getTs);
    dbc.addNumber("Odometry x", ()->drivetrain.getPose().getTranslation().getX());
    dbc.addNumber("Odometry y", ()->drivetrain.getPose().getTranslation().getY());
    dbc.addNumber("Odometry theta", ()->drivetrain.getPose().getRotation().getDegrees());

    limelight.setCamMode(CamMode.vision);
    limelight.setLedMode(LedMode.on);
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
    autonomousCommand = autonomousChooser.getSelected();
    if (autonomousCommand != null) {
      autonomousCommand.start();
    }
    //new CalibrateFeedForward().start();
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
