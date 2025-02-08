// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import java.util.Map;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
///mport com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.MathUtil;
//import edu.wpi.first.math.geometry.Pose2d;
//import edu.wpi.first.math.geometry.Rotation2d;
//import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.team1699.LoadedBeamBreak;
import frc.team1699.commands.AbsoluteFieldDrive;
import frc.team1699.commands.AimHeadingToSpeaker;
import frc.team1699.commands.AimPivotToSpeaker;
import frc.team1699.commands.IntakeCommand;
import frc.team1699.commands.ShootCommand;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.IntakeSubsystem;
import frc.team1699.subsystems.PivotSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import frc.team1699.subsystems.SwerveSubsystem;
import frc.team1699.subsystems.PivotSubsystem.PivotPosition;

public class RobotContainer {
  private CommandXboxController driverController;
  private CommandXboxController operatorController;
  private SwerveSubsystem swerve;
  private IntakeSubsystem intake;
  private IndexerSubsystem indexer;
  private ShooterSubsystem shooter;
  private PivotSubsystem pivot;

  Command drive;
  Command driveNorth;
  Command driveSouth;
  Command driveEast;
  Command driveWest;
  
  public RobotContainer() {
    driverController = new CommandXboxController(0);
    operatorController = new CommandXboxController(1);
    swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));
    intake = new IntakeSubsystem();
    indexer = new IndexerSubsystem();
    shooter = new ShooterSubsystem();
    pivot = new PivotSubsystem();
    drive = swerve.driveCommand(
    () -> MathUtil.applyDeadband(driverController.getLeftY() * -1, Constants.LEFT_Y_DEADBAND), 
    () -> MathUtil.applyDeadband(driverController.getLeftX() * -1, Constants.LEFT_X_DEADBAND), 
    () -> driverController.getRightX() * -1);

    driveNorth = new AbsoluteFieldDrive(swerve, 
    () -> MathUtil.applyDeadband(driverController.getLeftY() * -1, Constants.LEFT_Y_DEADBAND), 
    () -> MathUtil.applyDeadband(driverController.getLeftX() * -1, Constants.LEFT_X_DEADBAND),
    () -> 0);

    driveSouth = new AbsoluteFieldDrive(swerve, 
    () -> MathUtil.applyDeadband(driverController.getLeftY() * -1, Constants.LEFT_Y_DEADBAND), 
    () -> MathUtil.applyDeadband(driverController.getLeftX() * -1, Constants.LEFT_X_DEADBAND),
    () -> 1);

    driveEast = new AbsoluteFieldDrive(swerve, 
    () -> MathUtil.applyDeadband(driverController.getLeftY() * -1, Constants.LEFT_Y_DEADBAND), 
    () -> MathUtil.applyDeadband(driverController.getLeftX() * -1, Constants.LEFT_X_DEADBAND),
    () -> 1.5);

    driveWest = new AbsoluteFieldDrive(swerve, 
    () -> MathUtil.applyDeadband(driverController.getLeftY() * -1, Constants.LEFT_Y_DEADBAND), 
    () -> MathUtil.applyDeadband(driverController.getLeftX() * -1, Constants.LEFT_X_DEADBAND),
    () -> 0.5);

    NamedCommands.registerCommand("aimHubPosition", pivot.setIntakePosition());
    NamedCommands.registerCommand("aimIntakePosition", pivot.setIntakePosition());
    NamedCommands.registerCommand("intake", new IntakeCommand(intake, indexer));
    NamedCommands.registerCommand("shoot", new ShootCommand(indexer, shooter, .4, .4));
    NamedCommands.registerCommand("shootHard", new ShootCommand(indexer, shooter, .7, .7));
    NamedCommands.registerCommand("aimHeading", new AimHeadingToSpeaker(swerve));
    NamedCommands.registerCommand("aimPivot", new AimPivotToSpeaker(pivot));
    NamedCommands.registerCommand("waitUntilPivoted", pivot.waitUntilAimed());
    NamedCommands.registerCommand("aimAmpPosition", pivot.setAmpPosition());
    NamedCommands.registerCommand("shootAmp", new ShootCommand(indexer, shooter, 0.2, 0.05));
    
    configureBindings();    
  }

  /**
   * Operator Controls
   * RIGHT TRIGGER HOLD = INTAKE
   * LEFT TRIGGER HOLD = OUTTAKE
   * RIGHT BUMPER HOLD = SHOOT
   * LEFT BUMPER HOLD = AIM
   * B = PIVOT TO LOWEST POSSIBLE POSITION
   * A = PIVOT TO AMP
   * X = PIVOT TO POSITION WHICH IS HOPEFULLY SUBWOOFER
   * 
   * Driver Controls
   * NORMAL DRIVE CONTROLS
   * D PAD = DO CARDINAL DIRECTIONS
   * Y = RESET HEADING
   */
  private void configureBindings() {
    operatorController.rightTrigger()
      .onTrue(pivot.setIntakePosition())
      .whileTrue(new IntakeCommand(intake, indexer));
    
    operatorController.leftTrigger()
      .onTrue(pivot.setIntakePosition())
      .whileTrue(intake.reverse()
        .alongWith(indexer.reverse()))
      .onFalse(intake.stop()
        .alongWith(indexer.stop()));
    
    
    // operatorController.rightBumper()
    //   .whileTrue(new ShootCommand(indexer, shooter, .65, .65)
    //   .onlyIf(() -> pivot.getCurrentPosition() == PivotPosition.OTHER))
    //   .whileTrue(new ShootCommand(indexer, shooter, 0.1, 0.1)
    //   .onlyIf(() -> pivot.getCurrentPosition() == PivotPosition.AMP));
    operatorController.rightBumper().whileTrue(
      new SelectCommand<>(
          Map.of(
            PivotPosition.AMP, new ShootCommand(indexer, shooter, 0.2, 0.05), 
            PivotPosition.OTHER, new ShootCommand(indexer, shooter, .65, .65)), 
          pivot::getCurrentPosition));

    //changed shoot command to onTrue instead of whileTrue, then changed back
    driverController.povUp().whileTrue(driveNorth);
    driverController.povDown().whileTrue(driveSouth);
    driverController.povRight().whileTrue(driveEast);
    driverController.povLeft().whileTrue(driveWest);

    swerve.setDefaultCommand(drive);

    driverController.y().onTrue(Commands.runOnce(() -> {swerve.zeroGyro();}));

    operatorController.b().onTrue(pivot.setHomePosition());
    operatorController.a()
      .onTrue(pivot.setAmpPosition());
      //Changed shooter to shoot after lineing up
    operatorController.x().onTrue(pivot.setIntakePosition());
    
    operatorController.y().whileTrue((
      pivot.setIntakePosition()
        .alongWith(shooter.setMotorSpeedCommand(-.3)
        .alongWith(indexer.reverse())))
      .andThen(indexer.waitUntilLoaded())
      .andThen(shooter.setMotorSpeedCommand(-.3)
        .alongWith(indexer.reverse()))
      .andThen(indexer.waitUntilUnloaded())
      .andThen(new IntakeCommand(intake, indexer)))
      .onFalse(shooter.setMotorSpeedCommand(0));
    
      operatorController.leftBumper().whileTrue(new AimHeadingToSpeaker(swerve).alongWith(new AimPivotToSpeaker(pivot)));
  }

  public Command getAutonomousCommand() {
    // return AutoBuilder.buildAuto("Distance Four Piece");
    // return AutoBuilder.buildAuto("Cool Epic Humble Auto");
    return AutoBuilder.buildAuto("StartMidScoreLollipops");
  }
}
