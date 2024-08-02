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
        SHOOTERANGLE,
        IDLE
    }

    private final CANSparkMax shooterMotor;
    private final CANSparkMax shooterSwerveMotor;
    private final RelativeEncoder shooterEncoder;
    private double m_currentPosition;

    public Shooter() {
        shooterMotor = new CANSparkMax(Constants.Shooter.shooterID, CANSparkLowLevel.MotorType.kBrushless);
        shooterSwerveMotor = new CANSparkMax(Constants.Shooter.shooterSwerveID, CANSparkLowLevel.MotorType.kBrushless);
        shooterEncoder = shooterMotor.getEncoder();
    }
    @Override
    public void periodic() {
        m_currentPosition = shooterEncoder.getPosition();
    }

    public double getCurrentPosition() {
        return m_currentPosition;
    }

    public double getDegreesFromPosition(double position) {
        return position / (Constants.Shooter.gearratio / 360.0 );
    }
    public void setSpeed(double speed) {
        shooterMotor.set(speed);
    }

    public void setSwerveSpeed(double speed) {
        shooterSwerveMotor.set(speed);
    }
}
