package frc.robot.testpids;

import java.util.function.Supplier;
import com.spikes2212.dashboard.ConstantHandler;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PidSettings;
import frc.robot.Enums.Path;
import frc.robot.motionprofiling.FollowPath;

public class TestPIDMotionProfiling extends Command {
  // TODO: change names 
  private Supplier<Double> KP = ConstantHandler.addConstantDouble("KP", 0.01);
  private Supplier<Double> KP_ROTATION = ConstantHandler.addConstantDouble("KP_ROTATION", 0.01);
  private Supplier<Double> TURN_KP = ConstantHandler.addConstantDouble("TURN_KP", 0);
  private Supplier<Double> KD = ConstantHandler.addConstantDouble("KD", 0);
  private Supplier<Double> KD_ROTATION = ConstantHandler.addConstantDouble("KD_ROTATION", 0);
  private Supplier<Double> KV = ConstantHandler.addConstantDouble("KV", 0);
  private Supplier<Double> KV2 = ConstantHandler.addConstantDouble("KV2", 0);
  private Supplier<Double> KA = ConstantHandler.addConstantDouble("KA", 0);
  private Supplier<Double> KA2 = ConstantHandler.addConstantDouble("KA2", 0);

  private PidSettings pidSettings;
  private PidSettings pidSettings2;
  private double turnKp;
  private FollowPath testCommand;

  public TestPIDMotionProfiling() {
  }

  @Override
  protected void initialize() {
    updatePID();
    testCommand = new FollowPath(Path.RAMP_TO_ROCKET, pidSettings, pidSettings2, turnKp);
    testCommand.flip();
    testCommand.start();
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return testCommand.isCompleted();
  }

  @Override
  protected void end() {
    testCommand.cancel();
    testCommand.close();
  }

  public void updatePID(){
    this.pidSettings = new PidSettings(KP.get(), KD.get(), KV.get(), KA.get());
    this.pidSettings2 = new PidSettings(KP_ROTATION.get(), KD_ROTATION.get(), KV2.get(), KA2.get());
    turnKp = TURN_KP.get();
  }

  @Override
  protected void interrupted() {
    end();
  }
}

