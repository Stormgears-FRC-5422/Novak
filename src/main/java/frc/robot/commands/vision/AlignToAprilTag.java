package frc.robot.commands.vision;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotState;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.DrivetrainBase;
import frc.robot.subsystems.VisionSubsystem;
import frc.utils.vision.LimelightHelpers;

public class AlignToAprilTag extends Command {
    DrivetrainBase drivetrain;
    VisionSubsystem visionSubsystem;
    double visibleAprilTags;

    RobotState robotState;
    LimelightHelpers.LimelightTarget_Fiducial MiddleAprilTag;
    LimelightHelpers.LimelightTarget_Fiducial RightAprilTag;
    double middleAprilTag = Constants.Vision.middleAprilTag;
    double rightAprilTag = Constants.Vision.rightAprilTag;
    double distanceToTarget;
    double tx;
    double vy;
    double counter;

    public AlignToAprilTag(DrivetrainBase drivetrain, VisionSubsystem visionSubsystem) {
        this.drivetrain = drivetrain;
        this.visionSubsystem = visionSubsystem;
        this.robotState = RobotState.getInstance();
        addRequirements(drivetrain, visionSubsystem);
    }

    @Override
    public void execute() {
        visionSubsystem.changeToAprilTagPipeline();
        if (robotState.getShooterHeight() == RobotState.ShooterHeight.HIGH) {

            if (visionSubsystem.hasValidTarget()) {

                visibleAprilTags = visionSubsystem.getAprilTags().get().length;


                if (visibleAprilTags == 1) {

                    MiddleAprilTag = visionSubsystem.getAprilTags().get()[0].fiducialID == middleAprilTag ? visionSubsystem.getAprilTags().get()[0] : null;
                    RightAprilTag = visionSubsystem.getAprilTags().get()[0].fiducialID == rightAprilTag ? visionSubsystem.getAprilTags().get()[0] : null;
                    if (MiddleAprilTag != null) {

                        distanceToTarget = MiddleAprilTag.getRobotPose_TargetSpace2D().getX();
                        robotState.setDistanceToTarget(distanceToTarget);
                    } else {
                        robotState.setDistanceToTarget(0.0);
                    }
                } else if (visibleAprilTags == 2) {

                    MiddleAprilTag = visionSubsystem.getAprilTags().get()[0].fiducialID == middleAprilTag ? visionSubsystem.getAprilTags().get()[0] : visionSubsystem.getAprilTags().get()[1];
                    RightAprilTag = visionSubsystem.getAprilTags().get()[1].fiducialID == rightAprilTag ? visionSubsystem.getAprilTags().get()[1] : visionSubsystem.getAprilTags().get()[0];
                    if (MiddleAprilTag != null) {
                    } else {
                        robotState.setDistanceToTarget(0.0);
                    }

                    distanceToTarget = MiddleAprilTag.getRobotPose_TargetSpace2D().getX();
                    robotState.setDistanceToTarget(distanceToTarget);
                } else {
                    ChassisSpeeds speeds = new ChassisSpeeds(0.0, 0.0, 0.0);
                    drivetrain.percentOutputDrive(speeds, false);
                    robotState.setDistanceToTarget(0.0);
                }
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        ChassisSpeeds speeds = new ChassisSpeeds(0.0, 0.0, 0.0);
        drivetrain.percentOutputDrive(speeds, false);
        robotState.setDistanceToTarget(0.0);
    }

    @Override
    public boolean isFinished() {
        return (Math.abs(0-tx) < Constants.AlignToAprilTag.txOffset && Math.abs(0-vy) < Constants.AlignToAprilTag.vyOffset) || (counter > 10) || robotState.getShooterHeight() == RobotState.ShooterHeight.LOW;
    }
}
