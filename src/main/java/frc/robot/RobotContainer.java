// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.JoyStickDrive;
import frc.robot.commands.PigeonCommand;
import frc.robot.commands.Shoot;
import frc.robot.commands.vision.AlignToAprilTag;
import frc.robot.commands.vision.DriveToBall;
import frc.robot.commands.vision.TurnToNextBall;
import frc.robot.commands.vision.VisionIdle;
import frc.robot.joysticks.IllegalJoystickTypeException;
import frc.robot.joysticks.SolidworksJoystick;
import frc.robot.joysticks.SolidworksJoystickFactory;
import frc.robot.Constants.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VisionSubsystem;

import java.util.function.ToDoubleBiFunction;

import static frc.robot.Constants.ButtonBoard.driveJoystickPort;

public class RobotContainer {
    VisionSubsystem visionSubsystem;
    Intake intake;
    Pigeon pigeon;
    DrivetrainBase drivetrain;
    Shooter shooter;

    //Commands
    TurnToNextBall turnToNextBall;
    DriveToBall driveToBall;
    VisionIdle visionIdle;
    AlignToAprilTag alignToAprilTag;
    Shoot shoot;

    IntakeCommand intakeCommand;

    SolidworksJoystick joystick;

    private Relaytest relaytest;

    public RobotContainer() throws IllegalJoystickTypeException, IllegalDriveTypeException {

        if (Toggles.useController) {
            System.out.println("Making 1st joystick!");
            joystick = SolidworksJoystickFactory.getInstance(ButtonBoard.driveJoystick, driveJoystickPort);
        }

        if (Toggles.useDrive) {
            drivetrain = DrivetrainFactory.getInstance(Drive.driveType);
            System.out.println("Create drive type " + Drive.driveType);

            if (Toggles.useController) {
                JoyStickDrive driveWithJoystick = new JoyStickDrive(drivetrain, joystick);
                drivetrain.setDefaultCommand(driveWithJoystick);
            }
        }

        if (Toggles.useVision) {
            visionSubsystem = new VisionSubsystem(Vision.limelightId);
            turnToNextBall = new TurnToNextBall(drivetrain, visionSubsystem);
            driveToBall = new DriveToBall(drivetrain, visionSubsystem);
        }

        if (Toggles.useIntake) {
            intake = new Intake();
            intakeCommand = new IntakeCommand(intake);
        }

        //To implement a trigger, make a function in the following classes: SolidworksJoystick, SolidworksLogitechController, SolidworksXboxController, SolidworksDummyController
        if (Toggles.usePigeon) {
            pigeon = new Pigeon();
            pigeon.setDefaultCommand(new PigeonCommand(pigeon));
        }

        if (Toggles.useVision) {
//            visionSubsystem = new VisionSubsystem(Vision.limelightId);
            visionIdle = new VisionIdle(visionSubsystem);
            if (Toggles.useDrive) {
                alignToAprilTag = new AlignToAprilTag(drivetrain, visionSubsystem);
                turnToNextBall = new TurnToNextBall(drivetrain, visionSubsystem);
                driveToBall = new DriveToBall(drivetrain, visionSubsystem);
            }

        }
        if (Toggles.useShooter) {
            shooter = new Shooter();
            shoot = new Shoot(shooter);
        }

        configureBindings();
    }

    private void configureBindings() {
        System.out.println("configure button");
        if (Toggles.useIntake){
            new Trigger(()-> joystick.intake()).onTrue(intakeCommand);
        }

        Relaytest relaycontroller = new Relaytest();
        new Trigger(()->joystick.relayStart()).onTrue(new InstantCommand(()->relaycontroller.startActuator()));
        new Trigger(()->joystick.relayStop()).onTrue(new InstantCommand(()->relaycontroller.stopActuator()));
        new Trigger(()->joystick.relayOff()).onTrue(new InstantCommand(()->relaycontroller.offActuator()));
        if (Toggles.useShooter) {
            new Trigger(()->joystick.shoot()).onTrue(shoot);
        }

        //new InstantCommand(() ->relaytest.startActuator())
        //relaytest.startActuator()
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
