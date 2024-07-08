// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.Intake;

public class RobotContainer {
  // subsystems
  private Intake intake;

  // commands
  private IntakeCommand intakeCommand;

  public RobotContainer() {
    intake = new Intake();
    intakeCommand = new IntakeCommand(intake);
    configureBindings();
  }

  private void configureBindings() {
    // bindings for intake command
    XboxController controller = new XboxController(2);
    Trigger yButton = new JoystickButton(controller, XboxController.Button.kY.value);
    yButton.onTrue(intakeCommand);
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
