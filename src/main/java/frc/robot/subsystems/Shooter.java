package frc.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Shooter extends SubsystemBase {

    public enum ShooterState {
        FORWARD,
        REVERSE,
        OFF,
        WARMUP
    }

    private final CANSparkMax shooterMotor;
    private double m_currentPosition;
    private final RelativeEncoder m_alternateEncoder;
    ShooterState shooterState;
    double speed;

    public Shooter() {
        setShooterState(shooterState.OFF);
        shooterMotor = new CANSparkMax(Constants.Shooter.shooterID, CANSparkLowLevel.MotorType.kBrushless);
        shooterMotor.setInverted(true);
        m_alternateEncoder = shooterMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, 512);
    }

    @Override
    public void periodic() {
        shooterMotor.set(speed);
        m_currentPosition = m_alternateEncoder.getPosition();
//        System.out.println("alr encoder position: " + m_alternateEncoder.getPosition());
//        System.out.println("alr encoder velocity: " + m_alternateEncoder.getVelocity());
    }

    public double getCurrentPosition() {
        return m_currentPosition;
    }

    public double getDegreesFromPosition(double position) {
        return position / (Constants.Shooter.gearratio / 360.0);
    }

    public void setShooterState(ShooterState state) {
        switch (state) {
            case OFF -> {
                setSpeed(0.0);
            }
            case FORWARD -> {
                setSpeed(Constants.Shooter.shooterSpeed);
            }
            case REVERSE -> {
                setSpeed(-Constants.Shooter.shooterSpeed);
            }
            case WARMUP -> {
                setSpeed(Constants.Shooter.shooterWarmup);
            }
        }
    }

    public double getAppliedOutput() {
        return shooterMotor.getAppliedOutput();
    }
    void setSpeed(double speed) {
        this.speed = speed;
    }
}
