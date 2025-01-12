package frc.team1699.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.LoadedBeamBreak;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;

public class ShootCommand extends Command{
    private final IndexerSubsystem indexer;
    private final ShooterSubsystem shooter;
    private final LoadedBeamBreak beamBreak;
    private final double topSpeed, bottomSpeed;
    private double ticks;

    public ShootCommand(IndexerSubsystem indexer, ShooterSubsystem shooter, double topSpeed, double bottomSpeed) {
        this.indexer = indexer;
        this.shooter = shooter;
        this.beamBreak = LoadedBeamBreak.getInstance();
        this.topSpeed = topSpeed;
        this.bottomSpeed = bottomSpeed;
        addRequirements(indexer, shooter);
    }

    @Override
    public void initialize() {
        System.out.println("Hellow");
        shooter.setMotorSpeed(topSpeed, bottomSpeed);
        ticks = 0;
    }
    
    @Override
    public void execute() {
        if(ticks > 25) {
            indexer.setMotorSpeed(.5);
        } else {
            ticks++;
        }
        System.out.println(ticks);
    }

    @Override
    public boolean isFinished() {
        if(!beamBreak.loaded().getAsBoolean()) {
            return true;

        }
        System.out.println("done");
                return false;

    }

    @Override

    public void end(boolean interrupted) {
        System.out.println("done");

        indexer.setMotorSpeed(0);
        shooter.setMotorSpeed(0);
    }
}
