package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotState extends SubsystemBase {
    private static RobotState m_instance;
    public static RobotState getInstance() {
        if (m_instance != null) return m_instance;

        m_instance = new RobotState();
        return m_instance;
    }

    private boolean isBallDetected;

    public void setIsBallDetected(boolean detected) {
        isBallDetected = detected;
    }

    public boolean getIsBallDetected() {
        return isBallDetected;
    }
}
