package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotState;
import frc.utils.LimelightHelpers;

import java.util.Optional;

import static frc.utils.LimelightHelpers.getLatestResults;

public class VisionSubsystem extends SubsystemBase {
    RobotState robotState;
    String limelight;
    public VisionSubsystem(String limelight) {
        this.robotState = RobotState.getInstance();
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

}
