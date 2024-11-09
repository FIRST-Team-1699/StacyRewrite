package frc.team1699;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DigitalInput;

public class LoadedBeamBreak {
    private static LoadedBeamBreak beamBreak;

    public static LoadedBeamBreak getInstance() {
        if(beamBreak == null) {
            beamBreak = new LoadedBeamBreak();
        }
        return beamBreak;
    }

    private DigitalInput input;

    public LoadedBeamBreak() {
        input = new DigitalInput(2);
    }

    public BooleanSupplier loaded() {
        return () -> !input.get();
    }
}
