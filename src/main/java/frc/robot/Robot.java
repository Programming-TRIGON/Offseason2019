package frc.robot;

import com.spikes2212.dashboard.DashBoardController;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
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
    public static Compressor compressor;
    private Command autonomousCommand;
    private SendableChooser<Command> autonomousChooser = new SendableChooser<>();
    private DashBoardController dbc;

    @Override
    public void robotInit() {
        //compressor:
        compressor = new Compressor(1);
        compressor.start();
        // Utils:
        oi = new OI();
        pathCreater = new PathCreater();
        limelight = new Limelight();
        dbc = new DashBoardController();
        // Subsystems:
        cargoHolder = new CargoHolder();
        drivetrain = new Drivetrain();
        lift = new Lift();
        hatchHolder = new HatchHolder();

        // autonomousChooser.setDefaultOption("Default Auto", new ExampleCommand());
        // autonomousChooser.addOption("Auto", new AutoCommand());
        SmartDashboard.putData("Auto mode", autonomousChooser);
        SmartDashboard.putData("CalibrateDistance", new CalibrateDistance(oi.driver::getAButton));
        dbc.addNumber("limelight distance", limelight::getDistance);
        SmartDashboard.putData("Test PID vision", new TestPID());
        SmartDashboard.putData("clearPreferences", Commands.clearPreferences());
    }

    @Override
    public void robotPeriodic() {
        dbc.update();
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
