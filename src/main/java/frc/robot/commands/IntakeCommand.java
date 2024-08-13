// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Storage;

public class IntakeCommand extends StormCommand {
  /** Creates a new Intake. */
  private final Intake intake;
  Storage storage;
  private int counter;

  public IntakeCommand(Intake intake, Storage storage) {
    this.intake = intake;
    this.storage = storage;
    addRequirements(intake, storage);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    super.initialize();

    intake.setIntakeState(Intake.IntakeState.FORWARD);
    storage.setStorageState(Storage.StorageState.FORWARD);
    counter = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    counter++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.setIntakeState(Intake.IntakeState.OFF);
    storage.setStorageState(Storage.StorageState.OFF);
    super.end(interrupted);
  }

  @Override
  public boolean isFinished() {
    return counter >= Constants.Shooter.maxRunTime;
  }
}
