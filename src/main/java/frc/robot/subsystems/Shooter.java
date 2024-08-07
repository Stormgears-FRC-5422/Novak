package frc.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


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
    double speed;

    public Shooter() {
        setShooterState(shooterState.OFF);
        shooterMotor = new CANSparkMax(Constants.Shooter.shooterID, CANSparkLowLevel.MotorType.kBrushless);
        m_alternateEncoder = shooterMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, 8192);
    }

    @Override
    public void periodic() {
        shooterMotor.set(speed);

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

    public void setShooterState(shooterState state) {
        switch (state) {
            case OFF -> {
                setSpeed(0.0);
            }
            case FORWARD -> {
                setSpeed(0.15);
            }
            case BACKWARD -> {
                setSpeed(-0.15);
            }


        }
    }
    public double getAppliedOutput() {
        return shooterMotor.getAppliedOutput();

    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }


}
