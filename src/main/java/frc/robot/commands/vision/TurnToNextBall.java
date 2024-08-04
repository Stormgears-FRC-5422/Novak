package frc.robot.commands.vision;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotState;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.DrivetrainBase;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.Constants;

public class TurnToNextBall extends Command {
    VisionSubsystem visionSubsystem;
    DrivetrainBase drivetrain;
    double vxPercent = Constants.TurnToNextBall.vxPercent;
    double vyPercent = Constants.TurnToNextBall.vyPercent;
    double rotationSpeed = Constants.TurnToNextBall.omegaPercent;
    RobotState robotState;
    public TurnToNextBall(DrivetrainBase drivetrain, VisionSubsystem visionSubsystem) {
        this.drivetrain = drivetrain;
        this.visionSubsystem = visionSubsystem;
        robotState = RobotState.getInstance();
        addRequirements(drivetrain, visionSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Turn to next ball init");
    }

    @Override
    public void execute() {
        visionSubsystem.changeToDetectorPipeline();
        while (!visionSubsystem.hasValidTarget()) {
            drivetrain.percentOutputDrive(new ChassisSpeeds(vxPercent,vyPercent,rotationSpeed), false);
        }
        //turn clockwise to next ball

    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.percentOutputDrive(new ChassisSpeeds(0,0,0), false);
        System.out.println("Turn to next ball end");
    }

    @Override
    public boolean isFinished() {
        return visionSubsystem.getTennisBall().isPresent() || robotState.getShooterHeight() == RobotState.ShooterHeight.HIGH;
    }
}
