// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Storage;

public class IntakeCommand extends Command {
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
    intake.setIntakeState(Intake.IntakeState.FORWARD);
    counter = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    counter++;
    storage.setSpeed(-0.5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.setIntakeState(Intake.IntakeState.OFF);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
//    return counter > 50;
    return false;
  }
}
