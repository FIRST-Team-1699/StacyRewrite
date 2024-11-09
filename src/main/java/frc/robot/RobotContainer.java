// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.team1699.commands.IntakeCommand;
import frc.team1699.commands.ShootCommand;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.IntakeSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;

public class RobotContainer {
  private CommandXboxController controller;
  private IntakeSubsystem intake;
  private IndexerSubsystem indexer;
  private ShooterSubsystem shooter;

  public RobotContainer() {
    controller = new CommandXboxController(0);
    intake = new IntakeSubsystem();
    indexer = new IndexerSubsystem();
    shooter = new ShooterSubsystem();
    configureBindings();
  }

  private void configureBindings() {
    controller.rightTrigger()
      .whileTrue(new IntakeCommand(intake, indexer));
    
    controller.leftTrigger()
      .whileTrue(intake.reverse()
        .alongWith(indexer.reverse()))
      .onFalse(intake.stop()
        .alongWith(indexer.stop()));
    
    controller.rightBumper()
      .whileTrue(new ShootCommand(indexer, shooter, .5, .5));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
