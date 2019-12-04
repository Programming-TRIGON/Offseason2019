package frc.robot.testpids;

import java.util.function.Supplier;
import com.spikes2212.dashboard.ConstantHandler;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PidSettings;
import frc.robot.Enums.Path;
import frc.robot.motionprofiling.FollowPath;

public class TestPIDMotionProfiling extends Command {
  // TODO: change names 
  private Supplier<Double> kpLeft = ConstantHandler.addConstantDouble("KP Left", 0.01);
  private Supplier<Double> kpRight = ConstantHandler.addConstantDouble("KP Right", 0.01);
  private Supplier<Double> turnKpSupplier = ConstantHandler.addConstantDouble("TURN KP", 0);
  private Supplier<Double> kdLeft = ConstantHandler.addConstantDouble("KD Left", 0);
  private Supplier<Double> kdRight = ConstantHandler.addConstantDouble("KD Right", 0);
  private Supplier<Double> kvLeft = ConstantHandler.addConstantDouble("KV Left", 0);
  private Supplier<Double> kvRight = ConstantHandler.addConstantDouble("KV Right", 0);
  private Supplier<Double> kaLeft = ConstantHandler.addConstantDouble("KA Left", 0);
  private Supplier<Double> kaRight = ConstantHandler.addConstantDouble("KA Right", 0);

  private PidSettings leftSettings;
  private PidSettings rightSettings;
  private double turnKp;
  private FollowPath testCommand;

  public TestPIDMotionProfiling() {
  }

  @Override
  protected void initialize() {
    updatePID();
    testCommand = new FollowPath(Path.TEST_THREE_METERS, leftSettings, rightSettings, turnKp);
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
    this.leftSettings = new PidSettings(kpLeft.get(), kdLeft.get(), kvLeft.get(), kaLeft.get());
    this.rightSettings = new PidSettings(kpRight.get(), kdRight.get(), kvRight.get(), kaRight.get());
    turnKp = turnKpSupplier.get();
  }

  @Override
  protected void interrupted() {
    end();
  }
}

