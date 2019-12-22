package frc.robot.motionprofiling;

import com.spikes2212.dashboard.ConstantHandler;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Enums.RamsetePath;
import frc.robot.RobotConstants.MotionProfiling;
import wpilibj.controller.PIDController;
import wpilibj.controller.RamseteController;
import wpilibj.controller.SimpleMotorFeedforward;
import wpilibj.geometry.Pose2d;
import wpilibj.kinematics.ChassisSpeeds;
import wpilibj.kinematics.DifferentialDriveKinematics;
import wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import wpilibj.trajectory.Trajectory;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static frc.robot.Robot.drivetrain;

public class RamseteFollow extends Command {
  private final Timer m_timer = new Timer();
  private final boolean m_usePID;
  private final Trajectory m_trajectory;
  private final Supplier<Pose2d> m_pose;
  private final RamseteController m_follower;
  private final SimpleMotorFeedforward m_feedforward;
  private final DifferentialDriveKinematics m_kinematics;
  private final Supplier<DifferentialDriveWheelSpeeds> m_speeds;
  private final PIDController m_leftController;
  private final PIDController m_rightController;
  private final BiConsumer<Double, Double> m_output;
  private DifferentialDriveWheelSpeeds m_prevSpeeds;
  private double m_prevTime;
  private Supplier<Double> kpSupplier = ConstantHandler.addConstantDouble("Ramsete kp", 0);
  private boolean isTuning;

  /**
   * Constructs a new RamseteCommand that, when executed, will follow the provided trajectory.
   * PID control and feedforward are handled internally, and outputs are scaled -12 to 12
   * representing units of volts.
   *
   * <p>Note: The controller will *not* set the outputVolts to zero upon completion of the path -
   * this
   * is left to the user, since it is not appropriate for paths with nonstationary endstates.
   *
   * @param trajectory      The trajectory to follow.
   * @param pose            A function that supplies the robot pose - use one of
   *                        the odometry classes to provide this.
   * @param controller      The RAMSETE controller used to follow the trajectory.
   * @param feedforward     The feedforward to use for the drive.
   * @param kinematics      The kinematics for the robot drivetrain.
   * @param wheelSpeeds     A function that supplies the speeds of the left and
   *                        right sides of the robot drive.
   * @param leftController  The PIDController for the left side of the robot drive.
   * @param rightController The PIDController for the right side of the robot drive.
   * @param outputVolts     A function that consumes the computed left and right
   *                        outputs (in volts) for the robot drive.
   * @param requirement     The subsystems to require.
   */
  public RamseteFollow(Trajectory trajectory,
                       Supplier<Pose2d> pose,
                       RamseteController controller,
                       SimpleMotorFeedforward feedforward,
                       DifferentialDriveKinematics kinematics,
                       Supplier<DifferentialDriveWheelSpeeds> wheelSpeeds,
                       PIDController leftController,
                       PIDController rightController,
                       BiConsumer<Double, Double> outputVolts,
                       Subsystem requirement) {
    m_trajectory = trajectory;
    m_pose = pose;
    m_follower = controller;
    m_feedforward = feedforward;
    m_kinematics = kinematics;
    m_speeds = wheelSpeeds;
    m_leftController = leftController;
    m_rightController = rightController;
    m_output = outputVolts;

    m_usePID = true;

    requires(requirement);
  }

  public RamseteFollow(RamsetePath path) {
    this(path.getTrajectory(),
            drivetrain::getPose,
            new RamseteController(),
            new SimpleMotorFeedforward(MotionProfiling.KS, MotionProfiling.KV, MotionProfiling.KA),
            drivetrain.getKinematics(),
            drivetrain::getWheelSpeeds,
            new PIDController(MotionProfiling.MOTION_PROFILING_KP_TURN,0,0),
            new PIDController(0,0,0),
            drivetrain::voltageTankDrive,
            drivetrain);
  }
  public void enableTuning(){
    isTuning = true;
  }

  @Override
  public void initialize() {
    if(isTuning){
      m_leftController.setP(kpSupplier.get());
      m_rightController.setP(kpSupplier.get());
    }
    m_prevTime = 0;
    var initialState = m_trajectory.sample(0);
    m_prevSpeeds = m_kinematics.toWheelSpeeds(
            new ChassisSpeeds(initialState.velocityMetersPerSecond,
                    0,
                    initialState.curvatureRadPerMeter
                            * initialState.velocityMetersPerSecond));
    m_timer.reset();
    m_timer.start();
    if (m_usePID) {
      m_leftController.reset();
      m_rightController.reset();
    }
  }

  @Override
  public void execute() {
    double curTime = m_timer.get();
    double dt = curTime - m_prevTime;

    var targetWheelSpeeds = m_kinematics.toWheelSpeeds(
            m_follower.calculate(m_pose.get(), m_trajectory.sample(curTime)));

    var leftSpeedSetpoint = targetWheelSpeeds.leftMetersPerSecond;
    var rightSpeedSetpoint = targetWheelSpeeds.rightMetersPerSecond;

    double leftOutput;
    double rightOutput;

    if (m_usePID) {
      double leftFeedforward =
              m_feedforward.calculate(leftSpeedSetpoint,
                      (leftSpeedSetpoint - m_prevSpeeds.leftMetersPerSecond) / dt);

      double rightFeedforward =
              m_feedforward.calculate(rightSpeedSetpoint,
                      (rightSpeedSetpoint - m_prevSpeeds.rightMetersPerSecond) / dt);

      leftOutput = leftFeedforward
              + m_leftController.calculate(m_speeds.get().leftMetersPerSecond,
              leftSpeedSetpoint);

      rightOutput = rightFeedforward
              + m_rightController.calculate(m_speeds.get().rightMetersPerSecond,
              rightSpeedSetpoint);
    } else {
      leftOutput = leftSpeedSetpoint;
      rightOutput = rightSpeedSetpoint;
    }

    m_output.accept(leftOutput, rightOutput);

    m_prevTime = curTime;
    m_prevSpeeds = targetWheelSpeeds;
  }

  @Override
  public void end() {
    m_output.accept(0.0, 0.0);
    m_timer.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }

  @Override
  public boolean isFinished() {
    return m_timer.hasPeriodPassed(m_trajectory.getTotalTimeSeconds());
  }
}