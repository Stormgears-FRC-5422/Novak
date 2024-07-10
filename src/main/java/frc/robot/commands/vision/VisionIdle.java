package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotState;
import frc.robot.RobotState.VisionState;
import frc.robot.subsystems.VisionSubsystem;
import frc.utils.vision.LimelightHelpers;

public class VisionIdle extends Command {

    RobotState robotState;
    VisionState visionState;
    int visibleAprilTags;
    LimelightHelpers.LimelightTarget_Fiducial MiddleAprilTag;
    LimelightHelpers.LimelightTarget_Fiducial RightAprilTag;
    double middleAprilTag = Constants.Vision.middleAprilTag;
    double rightAprilTag = Constants.Vision.rightAprilTag;
    double distanceToTarget;

    @Override
    public void initialize() {
        visionState = VisionState.IDLE;
        robotState.setVisionState(visionState);
    }

    VisionSubsystem visionSubsystem;
    public VisionIdle(VisionSubsystem visionSubsystem) {
        this.robotState = RobotState.getInstance();
        this.visionSubsystem = visionSubsystem;
        addRequirements(visionSubsystem);
    }



    @Override
    public void execute() {
        if (robotState.getShooterHeight() == RobotState.ShooterHeight.HIGH) {
            VisionState visionState = VisionState.IDLE;
            robotState.setVisionState(visionState);

            if (visionSubsystem.getAprilTags().isPresent()) {
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

                        distanceToTarget = MiddleAprilTag.getRobotPose_TargetSpace2D().getX();
                        robotState.setDistanceToTarget(distanceToTarget);
                    } else {
                        robotState.setDistanceToTarget(0.0);
                    }

                }

            } else {
                robotState.setDistanceToTarget(0.0);
            }


        } else if (robotState.getShooterHeight() == RobotState.ShooterHeight.LOW) {

            if (visionSubsystem.getTennisBall().isPresent()) {

                double tx = visionSubsystem.getTennisBall().get().tx;
                if (tx > 0) {

                    VisionState visionState = VisionState.RIGHT;
                    robotState.setVisionState(visionState);

                } else if (tx < 0){

                    VisionState visionState = VisionState.LEFT;
                    robotState.setVisionState(visionState);

                }
            } else {

                VisionState visionState = VisionState.IDLE;
                robotState.setVisionState(visionState);

            }
        }




    }
    @Override
    public void end(boolean interrupted) {
        robotState.setVisionState(VisionState.IDLE);
        robotState.setDistanceToTarget(0.0);
    }
}
