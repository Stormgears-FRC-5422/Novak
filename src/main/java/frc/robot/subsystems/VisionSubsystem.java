package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.LimelightHelpers;

import java.util.Optional;

import static frc.utils.LimelightHelpers.getLatestResults;

public class VisionSubsystem extends SubsystemBase {
    String limelight;
    public VisionSubsystem(String limelight) {
        LimelightHelpers.setLEDMode_PipelineControl("");
        LimelightHelpers.setLEDMode_ForceBlink("");
        this.limelight = limelight;
    }
    public Optional<LimelightHelpers.LimelightTarget_Detector> getTennisBall(){
        var results = getLatestResults(limelight);
        if (results != null && results.valid && results.targets_Detector.length > 0 ) {
            return Optional.of(results.targets_Detector[0]);
        }
        return Optional.empty();
    }
    public Optional<LimelightHelpers.LimelightTarget_Fiducial> getAprilTags(){
        var results = getLatestResults(limelight);
        if (results != null && results.valid && results.targets_Fiducials.length > 0) {
            return Optional.of(results.targets_Fiducials[0]);
        }
        return Optional.empty();
    }

}
