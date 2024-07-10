// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.JoyStickDrive;
import frc.robot.commands.vision.AlignToAprilTag;
import frc.robot.commands.vision.DriveToBall;
import frc.robot.commands.vision.TurnToNextBall;
import frc.robot.commands.vision.VisionIdle;
import frc.robot.joysticks.IllegalJoystickTypeException;
import frc.robot.joysticks.SolidworksJoystick;
import frc.robot.joysticks.SolidworksJoystickFactory;
import frc.robot.Constants.*;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.VisionSubsystem;

public class RobotContainer {
  VisionSubsystem visionSubsystem;
  Intake intake;
  Drivetrain drivetrain;

  //Commands
  TurnToNextBall turnToNextBall;
  DriveToBall driveToBall;
  VisionIdle visionIdle;
  AlignToAprilTag alignToAprilTag;
  IntakeCommand intakeCommand;


  SolidworksJoystick joystick;
  public RobotContainer() throws IllegalJoystickTypeException {

    joystick = SolidworksJoystickFactory.getInstance(ButtonBoard.driveJoystick, ButtonBoard.driveJoystickPort);

    if (Toggles.useIntake) {
      intake = new Intake();
      intakeCommand = new IntakeCommand(intake);
    }
    if (Toggles.useDrive) {
      System.out.println("Create drive type " + Drive.driveType);
      drivetrain = new Drivetrain();
      if (Toggles.useController) {
        System.out.println("Making 1st joystick!");

        joystick = SolidworksJoystickFactory.getInstance(ButtonBoard.driveJoystick, ButtonBoard.driveJoystickPort);
        JoyStickDrive driveWithJoystick = new JoyStickDrive(drivetrain, joystick);
        drivetrain.setDefaultCommand(driveWithJoystick);
      }

    }
    if (Toggles.useVision) {
      visionSubsystem = new VisionSubsystem(Vision.limelightId);
        visionIdle = new VisionIdle(visionSubsystem);
      if (Toggles.useDrive) {
        alignToAprilTag = new AlignToAprilTag(drivetrain, visionSubsystem);
        turnToNextBall = new TurnToNextBall(drivetrain, visionSubsystem);
        driveToBall = new DriveToBall(drivetrain, visionSubsystem);
      }

    }
    configureBindings();
  }
  private void configureBindings() {
    if (Toggles.useVision) {
      visionSubsystem.setDefaultCommand(visionIdle);
    }

    if (Toggles.useVision && Toggles.useDrive){
      new Trigger(() -> joystick.drivetoBall()).whileTrue(turnToNextBall.andThen(driveToBall));

    }
    if (Toggles.useIntake) {
      new Trigger(() -> joystick.intake()).onTrue(intakeCommand);
    }
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
