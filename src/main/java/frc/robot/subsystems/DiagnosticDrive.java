package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DiagnosticDrive extends DrivetrainBase {
    TalonFX m_frontLeftDrive, m_frontRightDrive, m_backLeftDrive, m_backRightDrive;
    TalonFX m_frontLeftSteer, m_frontRightSteer, m_backLeftSteer, m_backRightSteer;
    TalonFX[] m_driveArray;
    TalonFX[] m_steerArray;

    public DiagnosticDrive(){
        m_frontLeftDrive = new TalonFX(11);
        m_frontRightDrive = new TalonFX(21);
        m_backRightDrive = new TalonFX(31);
        m_backLeftDrive = new TalonFX(41);

        m_frontLeftSteer = new TalonFX(10);
        m_frontRightSteer = new TalonFX(20);
        m_backRightSteer = new TalonFX(30);
        m_backLeftSteer = new TalonFX(40);




        m_driveArray = new TalonFX[]{m_frontLeftDrive, m_frontRightDrive,
            m_backLeftDrive, m_backRightDrive};
        m_steerArray = new TalonFX[]{m_frontLeftSteer, m_frontRightSteer,
            m_backLeftSteer, m_backRightSteer};
    }

    @Override
    public void periodic() {
        double driveSpeed = m_chassisSpeeds.vxMetersPerSecond;
        double steerSpeed = m_chassisSpeeds.omegaRadiansPerSecond;

        for (TalonFX m : m_driveArray) {
            m.set(driveSpeed);
        }
//
        for (TalonFX m : m_steerArray) {
            m.set(steerSpeed);
        }

    }
}
