package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.SparkLimitSwitch.Type;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.logging.Logger;


public class Shooter extends SubsystemBase {


    public enum shooterState {
        FORWARD,
        BACKWARD,
        OFF
    }

    private final CANSparkMax shooterMotor;
    private double m_currentPosition;
    private final RelativeEncoder m_alternateEncoder;
    shooterState shooterState;

    public Shooter() {
        shooterMotor = new CANSparkMax(Constants.Shooter.shooterID, CANSparkLowLevel.MotorType.kBrushless);
        m_alternateEncoder = shooterMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, 8192);
    }

    @Override
    public void periodic() {
        switch (shooterState) {
            case OFF -> {
                shooterMotor.set(0);
            }
            case FORWARD -> {
                shooterMotor.set(0.5);
            }
            case BACKWARD -> {
                shooterMotor.set(-0.5);
            }


        }
        m_currentPosition = m_alternateEncoder.getPosition();
        System.out.println("alr encoder position: " + m_alternateEncoder.getPosition());
        System.out.println("alr encoder velocity: " + m_alternateEncoder.getVelocity());
    }

    public double getCurrentPosition() {
        return m_currentPosition;
    }



    public double getDegreesFromPosition(double position) {
        return position / (Constants.Shooter.gearratio / 360.0);
    }

    public void setState(shooterState state) {
        this.shooterState = state;
    }
    public double getAppliedOutput() {
        return shooterMotor.getAppliedOutput();

    }


}
