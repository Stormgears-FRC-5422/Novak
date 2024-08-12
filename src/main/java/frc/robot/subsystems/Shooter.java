package frc.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.utils.motorcontrol.StormSpark;

public class Shooter extends SubsystemBase {
    public enum ShooterState {
        OFF,
        WARMUP,
        FORWARD,
        REVERSE,
        GATE_ONLY,
    }

    ShooterState shooterState;

    private final StormSpark shooterMotor;
    private final StormSpark gateMotor;

    private double m_currentPosition;
    private final RelativeEncoder m_alternateEncoder;
    double speed;
    double gatePower;

    public Shooter() {
        shooterMotor = new StormSpark(Constants.Shooter.shooterID, CANSparkLowLevel.MotorType.kBrushless, StormSpark.MotorKind.kNeo);
        shooterMotor.setInverted(true);

        m_alternateEncoder = shooterMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, Constants.Shooter.encoderTicksPerRev);

        gateMotor = new StormSpark(Constants.Shooter.shooterID, CANSparkLowLevel.MotorType.kBrushed, StormSpark.MotorKind.k550);
        gateMotor.setSmartCurrentLimit(1);
        gateMotor.setIdleMode(CANSparkBase.IdleMode.kBrake);

        setShooterState(ShooterState.OFF);
    }

    @Override
    public void periodic() {
        shooterMotor.set(speed);
        gateMotor.set(gatePower);
        if (gatePower > 0) {
            System.out.println("Gate output current: " + gateMotor.getOutputCurrent());
        }
//        m_currentPosition = m_alternateEncoder.getPosition();
//        System.out.println("alr encoder position: " + m_alternateEncoder.getPosition());
//        System.out.println("alr encoder velocity: " + m_alternateEncoder.getVelocity());
    }

    // TODO - we need to be smarter about the gate
    public void setShooterState(ShooterState state) {
        this.shooterState = state;
        switch (state) {
            case OFF -> {
                setSpeed(0.0);
                setGatePower(0.0);
            }
            case FORWARD -> {
                setSpeed(Constants.Shooter.shooterSpeed);
                setGatePower(Constants.Shooter.gatePower);
            }
            case REVERSE -> {
                setSpeed(-Constants.Shooter.shooterSpeed);
                setGatePower(Constants.Shooter.gatePower);
            }
            case WARMUP -> {
                setSpeed(Constants.Shooter.shooterWarmup);
                setGatePower(0.0);
            }
            case GATE_ONLY -> {
                setSpeed(0);
                setGatePower(Constants.Shooter.gatePower);
            }

        }
    }

    private void setSpeed(double speed) {
        this.speed = speed;
    }

    private void setGatePower(double power) { this.gatePower = power; }

//    public double getCurrentPosition() {
//        return m_currentPosition;
//    }

//    public double getDegreesFromPosition(double position) {
//        return position / (Constants.Shooter.gearratio / 360.0);
//    }

//    public double getAppliedOutput() {
//        return shooterMotor.getAppliedOutput();
//    }

}

