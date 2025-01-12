package frc.team1699.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.LimelightHelpers;
import frc.team1699.subsystems.SwerveSubsystem;

public class AimHeadingToSpeaker extends Command {
    private final SwerveSubsystem swerve;
    private PIDController headingController;

    public AimHeadingToSpeaker(SwerveSubsystem swerve) {
        this.swerve = swerve;
        this.headingController = new PIDController(.15, 0, 0);
        addRequirements(swerve);
    }

    @Override
    public void initialize() {}
    
    @Override
    public void execute() {
        double rotationOutput = headingController.calculate(LimelightHelpers.getTX("limelight"), 0);
        swerve.setChassisSpeeds(new ChassisSpeeds(0, 0, rotationOutput));
    }

    @Override
    public boolean isFinished() {
        if(Math.abs(LimelightHelpers.getTX("limelight")) <= 1 && LimelightHelpers.getTV("limelight")) {
            System.out.println("finished");
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
       // System.out.println("the end is neigh");
    }
}
