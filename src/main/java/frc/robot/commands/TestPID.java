package frc.robot.commands;

import com.spikes2212.dashboard.ConstantHandler;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.PidSettings;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestPID extends CommandGroup {
    private Consumer<PidSettings> setPID, setPID2;
    private Supplier<Double> KP = ConstantHandler.addConstantDouble("KP", 0.01);
    private Supplier<Double> KI = ConstantHandler.addConstantDouble("KI", 0);
    private Supplier<Double> KD = ConstantHandler.addConstantDouble("KD", 0);
    private Supplier<Double> KA = ConstantHandler.addConstantDouble("KA", 0);
    private Supplier<Double> KV = ConstantHandler.addConstantDouble("KV", 0);
    private Supplier<Double> TOLERANCE = ConstantHandler.addConstantDouble("tolerance", 1);
    private Supplier<Double> WAIT_TIME = ConstantHandler.addConstantDouble("wait time", 1);
    private Supplier<Double> KP_ROTATION = ConstantHandler.addConstantDouble("KP 2", 0.01);
    private Supplier<Double> KI_ROTATION = ConstantHandler.addConstantDouble("KI 2", 0);
    private Supplier<Double> KD_ROTATION = ConstantHandler.addConstantDouble("KD 2", 0);
    private Supplier<Double> TOLERANCE_ROTATION = ConstantHandler.addConstantDouble("tolerance 2", 1);
    private Supplier<Double> WAIT_TIME_ROTATION = ConstantHandler.addConstantDouble("wait time 2", 1);
    private Supplier<Double> KA2 = ConstantHandler.addConstantDouble("KA 2", 0);
    private Supplier<Double> KV2 = ConstantHandler.addConstantDouble("KV 2", 0);

    /**
     * @param command command to be run by testPID
     * @param setPID function to be called when TestPID want to change the PID values.
     */
    public TestPID(Command command, Consumer<PidSettings> setPID) {
        this.setPID = setPID;
        addSequential(command);
    }
    /**
     * @param command command to be run by testPID
     * @param setPID function to be called when TestPID want to change the PID values.
     * @param setPID2 the second function to be called for the second PIDController.
     */
    public TestPID(Command command, Consumer<PidSettings> setPID, Consumer<PidSettings> setPID2) {
        this(command, setPID);
        this.setPID2 = setPID2;
    }

    @Override
    protected void initialize() {
        //setting values for the first PIDController
        PidSettings pidSettings = new PidSettings(KP.get(), KI.get(), KD.get(), TOLERANCE.get(), WAIT_TIME.get());
        pidSettings.setKA(KA.get());
        pidSettings.setKA(KV.get());
        setPID.accept(pidSettings);
        if (setPID2 != null) {
            //the command has two PID controllers
            PidSettings pidSettings2 = new PidSettings(KP_ROTATION.get(), KI_ROTATION.get(), KD_ROTATION.get(), TOLERANCE_ROTATION.get(), WAIT_TIME_ROTATION.get());
            pidSettings.setKA(KA2.get());
            pidSettings.setKV(KV2.get());
            setPID2.accept(pidSettings2);
        }
    }
}
