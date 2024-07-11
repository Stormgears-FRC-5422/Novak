package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotState extends SubsystemBase {
    public enum VisionState {
        LEFT,
        RIGHT,
        IDLE
    }
    public enum ShooterHeight {
        LOW,
        HIGH
    }
    private static VisionState visionState;
    private static RobotState m_instance;

    public double getDistanceToTarget() {
        return distanceToTarget;
    }

    public void setDistanceToTarget(double distanceToTarget) {
        this.distanceToTarget = distanceToTarget;
    }

    private boolean isBallDetected;
    private ShooterHeight shooterHeight;
    private double distanceToTarget;

    public ShooterHeight getShooterHeight() {
        return shooterHeight;
    }

    public void setShooterHeight(ShooterHeight shooterHeight) {
        this.shooterHeight = shooterHeight;
    }

    public static RobotState getInstance() {
        if (m_instance != null) return m_instance;

        m_instance = new RobotState();
        return m_instance;
    }

   public void setVisionState(VisionState state) {
        visionState = state;
    }
    public VisionState getVisionState() {
        return visionState;
    }

    public void setIsBallDetected(boolean detected) {
        isBallDetected = detected;
    }

    public boolean getIsBallDetected() {
        return isBallDetected;
    }

    public Rotation2d getHeading() {
        return new Rotation2d();
    }
}
