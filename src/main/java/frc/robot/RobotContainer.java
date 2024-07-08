// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.vision.DriveToBall;
import frc.robot.commands.vision.TurnToNextBall;
import frc.robot.joysticks.IllegalJoystickTypeException;
import frc.robot.joysticks.SolidworksJoystick;
import frc.robot.joysticks.SolidworksJoystickFactory;
import frc.robot.Constants.*;
import frc.robot.subsystems.VisionSubsystem;

public class RobotContainer {
  //Subsystems
  VisionSubsystem visionSubsystem;

  //Commands
  TurnToNextBall turnToNextBall;
  DriveToBall driveToBall;

  SolidworksJoystick joystick;
  public RobotContainer() throws IllegalJoystickTypeException {
    configureBindings();
    joystick = SolidworksJoystickFactory.getInstance(ButtonBoard.driveJoystick,ButtonBoard.driveJoystickPort);
    if (Toggles.useVision) {
        visionSubsystem = new VisionSubsystem(Vision.limelightId);
        turnToNextBall = new TurnToNextBall(visionSubsystem);
        driveToBall = new DriveToBall(visionSubsystem);
    }
    //To implement a trigger, make a function in the following classes: SolidworksJoystick, SolidworksLogitechController, SolidworksXboxController, SolidworksDummyController
  }

  private void configureBindings() {
    if (Toggles.useVision && Toggles.useDrive){
      new Trigger(() -> joystick.driveNoteCancel()).whileFalse(new TurnToNextBall(visionSubsystem).andThen(new DriveToBall(visionSubsystem)));

    }
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
