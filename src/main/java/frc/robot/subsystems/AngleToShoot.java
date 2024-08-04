//// Copyright (c) FIRST and other WPILib contributors.
//// Open Source Software; you can modify and/or share it under the terms of
//// the WPILib BSD license file in the root directory of this project.
//
//package frc.robot.commands.shoot;
//
//import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.Subsystem;
//import frc.robot.subsystems.Shooter;
//
//public class AngleToShoot extends Command {
//    private final Shooter shooter;
//    private int counter;
//    private double targetHeight;
//    private double currentPosition;
//
//    public AngleToShoot(Shooter shooter) {
//        this.shooter = shooter;
//        addRequirements((Subsystem) shooter);
//        targetHeight=5;
//
//    }
//
//
//    @Override
//    public void initialize() {
//    }
//
//    @Override
//    public boolean isFinished() {
//        return (shooter.getDegreesFromPosition(currentPosition) == targetHeight);
//    }
//
//    @Override
//    public void execute() {
//        currentPosition=shooter.getCurrentPosition();
//        shooter.setSpeed(1.0);
//    }
//
//
//}