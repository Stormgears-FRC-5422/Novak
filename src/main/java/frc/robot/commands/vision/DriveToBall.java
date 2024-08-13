package frc.robot.commands.vision;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotState;
import frc.robot.joysticks.SolidworksXboxController;
import frc.robot.subsystems.drive.Drivetrain;
import frc.robot.subsystems.drive.DrivetrainBase;
import frc.robot.subsystems.VisionSubsystem;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;

public class DriveToBall extends Command {
    private static int TARGET =  -Constants.DriveToBall.targetOffset;
    private final PIDController translationController = new PIDController(Constants.DriveToBall.translationKp,
            Constants.DriveToBall.translationKi, Constants.DriveToBall.translationKd);
    private final PIDController rotationController = new PIDController(Constants.DriveToBall.rotationKp,
            Constants.DriveToBall.rotationKi, Constants.DriveToBall.rotationKd);

    DrivetrainBase drivetrain;
    SolidworksXboxController controller;
    double tx;
    double ty;
    NetworkTable table;
    VisionSubsystem visionSubsystem;
    int count = 0;
    double movement = 0;
    double rotation = 0;

    public DriveToBall(DrivetrainBase drivetrain, VisionSubsystem visionSubsystem) {
        translationController.setSetpoint(0.0);
        rotationController.setSetpoint(0.0);
        this.drivetrain = drivetrain;
        this.visionSubsystem = visionSubsystem;
        addRequirements(drivetrain, visionSubsystem);
        System.out.println("DriveToBall() working");
    }

    @Override
    public void initialize() {
        count = 0;
        movement = 0;
        rotation = 0;
        translationController.setSetpoint(0.0);
        rotationController.setSetpoint(0.0);
        System.out.println("DriveToBall::initialize() working");
    }

    @Override
    public void execute() {
        rotation = 0;
        System.out.println(visionSubsystem.getTX());
        if (visionSubsystem.hasValidTarget()) {
            tx = visionSubsystem.getTX();
            ty = visionSubsystem.getTY();
            movement = translationController.calculate(TARGET - ty);
            rotation = rotationController.calculate(tx);
            System.out.println("Note detected");
            count = 0;
        } else {
            count++;
        }
        System.out.println("Movement: " + movement);
        System.out.println("ROT: " + rotation);
        System.out.println("count: " + count);
        ChassisSpeeds speeds = new ChassisSpeeds(movement, 0, rotation);
        drivetrain.drive(speeds, false, 1);
        Logger.recordOutput("Tx" + tx);
        Logger.recordOutput("Ty" + ty);

    }


    @Override
    public boolean isFinished() {
//        return RobotState.getInstance().getShooterState() == Shooter.ShooterState.STAGED_FOR_SHOOTING;
        return count > 10;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Tx: " + tx);
        System.out.println("Ty: " + ty);
        ChassisSpeeds speeds;
        speeds = new ChassisSpeeds(0, 0, 0);
        drivetrain.percentOutputDrive(speeds, false);
        System.out.println("Drive To Note Finished: interrupted:" + interrupted);
    }
}
