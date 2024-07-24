package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotState;
import frc.utils.vision.LimelightExtra;
import frc.utils.vision.LimelightHelpers;

import java.util.Arrays;
import java.util.Optional;

import static frc.utils.vision.LimelightHelpers.getLatestResults;
import static frc.utils.vision.LimelightHelpers.setCameraPose_RobotSpace;

public class VisionSubsystem extends SubsystemBase {
    RobotState robotState;
    String limelight;
    public VisionSubsystem(String limelight) {
        this.robotState = RobotState.getInstance();
        LimelightHelpers.setLEDMode_PipelineControl("");
        LimelightHelpers.setLEDMode_ForceBlink("");
        this.limelight = limelight;
    }
    //optionally returns Detector results or a null if no results
    public Optional<LimelightHelpers.LimelightTarget_Detector> getTennisBall(){
        var results = getLatestResults(limelight);
        System.out.println(results.valid);
        System.out.println(Arrays.toString(results.targets_Detector));
        System.out.println(results.targets_Detector.length);
        if (results != null && results.valid && results.targets_Detector.length > 0 ) {
            System.out.println("return tenny");
            return Optional.of(results.targets_Detector[0]);
        }
        return Optional.empty();
    }
    //optionally returns AprilTag results
    public Optional<LimelightHelpers.LimelightTarget_Fiducial> getAprilTag(){
        var results = getLatestResults(limelight);
        if (results != null && results.valid && results.targets_Fiducials.length > 0) {
            return Optional.of(results.targets_Fiducials[0]);
        }
        return Optional.empty();
    }
    public double[] getTXandTY(){
        LimelightExtra limelightExtra = new LimelightExtra();
        return limelightExtra.getTXandTY(limelight);
    }

    @Override
    public void periodic() {
        isNoteDetected();
    }

    public boolean isNoteDetected() {
        var results = getLatestResults(limelight);
        if (results == null) {
            robotState.setIsBallDetected(false);
            return false;
        }

        if (results != null && results.valid && results.targets_Detector.length > 0) {
            boolean isPresent = Optional.of(results.targets_Detector[0]).isPresent();
            robotState.setIsBallDetected(isPresent);
            return isPresent;
        }
        robotState.setIsBallDetected(false);
        return false;
    }
    public Optional<LimelightHelpers.LimelightTarget_Fiducial[]> getAprilTags(){
        var results = getLatestResults(limelight);
        if (results != null && results.valid && results.targets_Fiducials.length > 0) {
            return Optional.of(results.targets_Fiducials);
        }
        return Optional.empty();
    }
    public Optional<Pose3d> getBotPose_TargetSpace(){
        Pose3d botPose3dTargetSpace = LimelightHelpers.getBotPose3d_TargetSpace(limelight);
        return Optional.of(botPose3dTargetSpace);

    }


}
