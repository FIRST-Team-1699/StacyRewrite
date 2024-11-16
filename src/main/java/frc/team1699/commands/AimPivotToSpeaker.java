package frc.team1699.commands;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.LimelightHelpers;
import frc.team1699.subsystems.PivotSubsystem;

public class AimPivotToSpeaker extends Command {
    private final PivotSubsystem pivot;
    private InterpolatingDoubleTreeMap table;

    public AimPivotToSpeaker(PivotSubsystem pivot) {
        table = new InterpolatingDoubleTreeMap();
        // -25.5
        table.put(14.9, 58.0 - 25.5);
        table.put(14.5, 57.0 - 25.5);
        table.put(14.0, 56.0 - 25.5);
        table.put(9.0, 52.0 - 25.5);
        table.put(7.0, 47.0 - 25.5);
        table.put(4.0, 45.0 - 25.5);
        table.put(0.0, 42.0 - 25.5);
        table.put(-3.5, 12.0);
        table.put(-7.0, 7.5);
        table.put(-10.0, 5.5);
        table.put(-12.0, 5.0);
        table.put(-14.0, 29.0 - 25.5);
        table.put(-15.0, 28.8 - 25.5);
        table.put(-16.0, 27.2 - 25.5);
        table.put(-17.0, 26.0 - 25.5);
        table.put(-18.5, 0.0);

        this.pivot = pivot;
        addRequirements(pivot);
    }

    @Override
    public void initialize() {}
    
    @Override
    public void execute() {
        if(LimelightHelpers.getTV("limelight")) {
            pivot.setSetpoint(table.get(LimelightHelpers.getTY("limelight")));
        }
    }

    @Override
    public boolean isFinished() {
        if(pivot.atSetpoint()) {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {}
}
