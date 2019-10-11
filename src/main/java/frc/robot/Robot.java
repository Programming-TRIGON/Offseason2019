package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.SetLiftHeight;
import frc.robot.commands.TestPID;
import frc.robot.commands.VisionPID;
import frc.robot.motionprofiling.FollowPath;
import frc.robot.motionprofiling.Path;
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

    @Override
    public void robotInit() {
        //compressor:
        compressor = new Compressor(1);
        compressor.start();
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
        SmartDashboard.putData("Auto mode", autonomousChooser);
        SetLiftHeight liftCommand = new SetLiftHeight(Enums.LiftHeights.RocketHatchMiddle);
        SmartDashboard.putData("Test PID lift", new TestPID(liftCommand, liftCommand::setPID));
        VisionPID visionCommand = new VisionPID(Enums.Target.RocketMiddle, true);
        SmartDashboard.putData("Test PID vision", new TestPID(visionCommand, visionCommand::setPID,
                visionCommand::setPID2));
        FollowPath motionProfileCommand = new FollowPath(Path.TEST);
        SmartDashboard.putData("Test PIDVA motion profile", new TestPID(motionProfileCommand,
                motionProfileCommand::setPID, motionProfileCommand::setPID2));
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
