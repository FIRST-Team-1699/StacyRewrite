// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
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

public class RobotContainer {
  private CommandXboxController controller;
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
    controller = new CommandXboxController(0);
    swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));
    intake = new IntakeSubsystem();
    indexer = new IndexerSubsystem();
    shooter = new ShooterSubsystem();
    pivot = new PivotSubsystem();
    drive = swerve.driveCommand(
    () -> MathUtil.applyDeadband(controller.getLeftY() * -1, Constants.LEFT_Y_DEADBAND), 
    () -> MathUtil.applyDeadband(controller.getLeftX() * -1, Constants.LEFT_X_DEADBAND), 
    () -> controller.getRightX() * -1);

    driveNorth = new AbsoluteFieldDrive(swerve, 
    () -> MathUtil.applyDeadband(controller.getLeftY() * -1, Constants.LEFT_Y_DEADBAND), 
    () -> MathUtil.applyDeadband(controller.getLeftX() * -1, Constants.LEFT_X_DEADBAND),
    () -> 0);

    driveSouth = new AbsoluteFieldDrive(swerve, 
    () -> MathUtil.applyDeadband(controller.getLeftY() * -1, Constants.LEFT_Y_DEADBAND), 
    () -> MathUtil.applyDeadband(controller.getLeftX() * -1, Constants.LEFT_X_DEADBAND),
    () -> 1);

    driveEast = new AbsoluteFieldDrive(swerve, 
    () -> MathUtil.applyDeadband(controller.getLeftY() * -1, Constants.LEFT_Y_DEADBAND), 
    () -> MathUtil.applyDeadband(controller.getLeftX() * -1, Constants.LEFT_X_DEADBAND),
    () -> 1.5);

    driveWest = new AbsoluteFieldDrive(swerve, 
    () -> MathUtil.applyDeadband(controller.getLeftY() * -1, Constants.LEFT_Y_DEADBAND), 
    () -> MathUtil.applyDeadband(controller.getLeftX() * -1, Constants.LEFT_X_DEADBAND),
    () -> 0.5);

    NamedCommands.registerCommand("aimHubPosition", pivot.setTestPositionTwo());
    NamedCommands.registerCommand("intake", new IntakeCommand(intake, indexer));
    NamedCommands.registerCommand("shoot", new ShootCommand(indexer, shooter, .4, .4));
    NamedCommands.registerCommand("shootHard", new ShootCommand(indexer, shooter, .7, .7));
    NamedCommands.registerCommand("aimHeading", new AimHeadingToSpeaker(swerve));
    NamedCommands.registerCommand("aimPivot", new AimPivotToSpeaker(pivot));
    NamedCommands.registerCommand("waitUntilPivoted", pivot.waitUntilAimed());
    
    configureBindings();    
  }

  private void configureBindings() {
    controller.rightTrigger()
      .onTrue(pivot.setTestPositionTwo())
      .whileTrue(new IntakeCommand(intake, indexer));
    
    controller.leftTrigger()
      .onTrue(pivot.setTestPositionTwo())
      .whileTrue(intake.reverse()
        .alongWith(indexer.reverse()))
      .onFalse(intake.stop()
        .alongWith(indexer.stop()));
    
    controller.rightBumper()
      .whileTrue(new ShootCommand(indexer, shooter, .65, .65));

    controller.povUp().whileTrue(driveNorth);
    controller.povDown().whileTrue(driveSouth);
    controller.povRight().whileTrue(driveEast);
    controller.povLeft().whileTrue(driveWest);
    swerve.setDefaultCommand(drive);

    controller.y().onTrue(Commands.runOnce(() -> {swerve.zeroGyro();}));

    controller.a().onTrue(pivot.setHomePosition());
    controller.b().onTrue(pivot.setTestPositionOne());
    controller.x().onTrue(pivot.setTestPositionTwo());
    controller.leftBumper().whileTrue(new AimHeadingToSpeaker(swerve).alongWith(new AimPivotToSpeaker(pivot)));
  }

  public Command getAutonomousCommand() {
    // return AutoBuilder.buildAuto("Distance Four Piece");
    // return AutoBuilder.buildAuto("Cool Epic Humble Auto");
    return AutoBuilder.buildAuto("NonChilant Auto");
  }
}
