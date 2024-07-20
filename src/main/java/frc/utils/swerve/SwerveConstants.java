package frc.utils.swerve;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.robot.Constants;


public final class SwerveConstants {

    static double maxVelocityMetersPerSecond = Constants.SparkMax.FreeSpeedRPM / 60.0 * Constants.Drive.wheelDiameter * Math.PI;

    public static final CANcoder mFrontLeftCancoder = new CANcoder(1);
    public static final CANcoder mFrontRightCancoder = new CANcoder(2);
    public static final CANcoder mBackLeftCancoder = new CANcoder(3);
    public static final CANcoder mBackRightCancoder = new CANcoder(4);

    public static final double angleGearRatio = (1 / 6.75);


    public static TalonFXConfiguration DriveFXConfig() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.Slot0.kP = 0.030 * 12.0;
        config.Slot0.kI = 0.0;
        config.Slot0.kD = 0.000001 * 12.0;
        config.Slot0.kS = 0.1;
        config.Slot0.kV = 12 * Math.PI * Constants.Drive.wheelDiameter / (Constants.Drive.driveGearRatio * maxVelocityMetersPerSecond);

        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.StatorCurrentLimit = 110;

        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = 90;
        config.CurrentLimits.SupplyTimeThreshold = 0.5;

        config.Voltage.PeakForwardVoltage = 12.0;
        config.Voltage.PeakReverseVoltage = -12.0;

        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        config.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.25;
        config.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.25;
        return config;
    }

    public static TalonFXConfiguration AngleFXConfig() {
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.Slot0.kP = 0.1;
        config.Slot0.kI = 0.0;
        config.Slot0.kD = 0.0000;
        config.Slot0.kS = 0.0;
        config.Slot0.kV = 0.0;

        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.StatorCurrentLimit = 80;

        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = 60;
        config.CurrentLimits.SupplyTimeThreshold = 0.2;

        config.Voltage.PeakForwardVoltage = 12.0;
        config.Voltage.PeakReverseVoltage = -12.0;

        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        return config;
    }

}
