package frc.team1699.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.LoadedBeamBreak;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command {
    private final IntakeSubsystem intake;
    private final IndexerSubsystem indexer;
    private final LoadedBeamBreak beamBreak;

    public IntakeCommand(IntakeSubsystem intake, IndexerSubsystem indexer) {
        this.intake = intake;
        this.indexer = indexer;
        this.beamBreak = LoadedBeamBreak.getInstance();
        addRequirements(intake, indexer);
    }

    @Override
    public void initialize() {
        intake.setMotorSpeed(.4);
        indexer.setMotorSpeed(.4);
    }
    
    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        if(beamBreak.loaded().getAsBoolean()) {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intake.setMotorSpeed(0);
        indexer.setMotorSpeed(0);
    }
}
