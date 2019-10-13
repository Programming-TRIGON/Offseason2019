package frc.robot.testpids;

import java.util.function.Supplier;
import com.spikes2212.dashboard.ConstantHandler;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.PidSettings;
import frc.robot.Enums.LiftHeights;
import frc.robot.commands.SetLiftHeight;

public class TestPIDLift extends Command {
  private Supplier<Double> KP = ConstantHandler.addConstantDouble("KP_lift", 0.0);
  private Supplier<Double> KI = ConstantHandler.addConstantDouble("KI_lift", 0);
  private Supplier<Double> KD = ConstantHandler.addConstantDouble("KD_lift", 0);
  private Supplier<Double> TOLERANCE = ConstantHandler.addConstantDouble("TOLERANCE_lift", 0);
  private Supplier<Double> DELTA_TOLERANCE = ConstantHandler.addConstantDouble("WAIT TIME_lift", 0);

  private PidSettings pidSettings;
  private Command testCommand;

  public TestPIDLift() {
  }

  @Override
  protected void initialize() {
    updatePID();
    testCommand = new SetLiftHeight(LiftHeights.RocketCargoBottom, this.pidSettings);
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
    this.pidSettings = new PidSettings(KP.get(), KI.get(), KD.get(), TOLERANCE.get(), DELTA_TOLERANCE.get());
    SmartDashboard.putString("PID setting", "" + KP.get() + KI.get() + KD.get() + TOLERANCE.get() + DELTA_TOLERANCE.get());
  }

  @Override
  protected void interrupted() {
    end();
  }
}

