package frc.robot.commands.vision;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.Constants;

public class TurnToNextBall extends Command {
    VisionSubsystem visionSubsystem;
    Drivetrain drivetrain;
    double vxPercent = Constants.TurnToNextBall.vxPercent;
    double vyPercent = Constants.TurnToNextBall.vyPercent;
    double rotationSpeed = Constants.TurnToNextBall.omegaPercent;
    public TurnToNextBall(Drivetrain drivetrain, VisionSubsystem visionSubsystem) {
        this.drivetrain = drivetrain;
        this.visionSubsystem = visionSubsystem;
        addRequirements(drivetrain, visionSubsystem);
    }


    @Override
    public void execute() {
        while (!visionSubsystem.getTennisBall().isPresent()) {
            drivetrain.percentOutputDrive(new ChassisSpeeds(vxPercent,vyPercent,rotationSpeed), false);
        }
        //turn clockwise to next ball

    }

    @Override
    public boolean isFinished() {
        return visionSubsystem.getTennisBall().isPresent();
    }
}
