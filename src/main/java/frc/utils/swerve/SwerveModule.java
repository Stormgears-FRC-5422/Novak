package frc.utils.swerve;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Constants;
import frc.utils.Conversions;
import static frc.utils.Conversions.MPSToRPS;


public class SwerveModule extends SubsystemBase {
    private final int moduleNumber;
    private final double angleOffset;

    private TalonFX angleMotor;
    private TalonFX driveMotor;
    private CANcoder angleEncoder;

    private double targetAngle;
    private double targetVelocity;

    private ControlRequest rotationDemand;
    private ControlRequest driveDemand;

    private double rotationVelocity;
    private double driveVelocity;
    private double drivePosition;


    public SwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants, CANcoder cancoder) {
        this.moduleNumber = moduleNumber;
        this.angleOffset = moduleConstants.angleOffset;

        angleEncoder = cancoder;

        angleMotor = new TalonFX(moduleConstants.angleMotorID);
        driveMotor = new TalonFX(moduleConstants.driveMotorID);

        angleMotor.getConfigurator().apply(SwerveConstants.AngleFXConfig(), Constants.Drive.CANTimeoutMs);
        driveMotor.getConfigurator().apply(SwerveConstants.DriveFXConfig(), Constants.Drive.CANTimeoutMs);

        driveMotor.setPosition(0);

        resetToAbsolute();

    }


    public void resetToAbsolute() {
//        need to check if this is the right way to do this
        angleEncoder.getAbsolutePosition().waitForUpdate(Constants.Drive.CANTimeoutMs);
        angleMotor.setPosition(getAbsolutePosition() - angleOffset / 360, Constants.Drive.CANTimeoutMs);
    }

    public double getAbsolutePosition() {
        return angleEncoder.getAbsolutePosition().getValue() * 360;
    }

    public double getCurrentDegrees() {
        return angleMotor.getRotorPosition().getValue() * 360;
    }

    public void setVelocity(SwerveModuleState desiredState) {
        double flip = setSteeringAngleOptimized(desiredState.angle) ? -1 : 1;
        targetVelocity = desiredState.speedMetersPerSecond * flip;
        double rotorSpeed = MPSToRPS(
                targetVelocity,
                Constants.Drive.wheelDiameter * Math.PI,
                Constants.Drive.driveGearRatio);

        if (Math.abs(rotorSpeed) < 0.002) {
            driveDemand = new NeutralOut();
        } else {
            driveDemand = new VelocityVoltage(rotorSpeed);
        }
    }

    private boolean setSteeringAngleOptimized(Rotation2d steerAngle) {
        boolean flip = false;
        final double targetClamped = steerAngle.getDegrees();
        final double angleUnclamped = getCurrentDegrees();
        final Rotation2d angleClamped = Rotation2d.fromDegrees(angleUnclamped);
        final Rotation2d relativeAngle = Rotation2d.fromDegrees(targetClamped).rotateBy(Conversions.inverse(angleClamped));
        double relativeDegrees = relativeAngle.getDegrees();
        if (relativeDegrees > 90.0) {
            relativeDegrees -= 180.0;
            flip = true;

        } else if (relativeDegrees < -90.0) {
            relativeDegrees += 180.0;
            flip = true;
        }
        setSteeringAngleRaw(angleUnclamped + relativeDegrees);
        targetAngle = angleUnclamped + relativeDegrees;
        return flip;
    }

    private void setSteeringAngleRaw(double angleDegrees) {
        double rotorPosition = angleDegrees / 360;
        rotationDemand = new PositionDutyCycle(rotorPosition, 0.0, true, 0.0, 0, false, false, false);
    }

    private double getCurrentVelocity() {
        return Conversions.RPSToMPS(
                driveVelocity,
                Constants.Drive.wheelDiameter * Math.PI,
                Constants.Drive.driveGearRatio);
    }


    public void setDriveNeutralBrake(boolean wantBrake) {
        TalonFXConfiguration t = new TalonFXConfiguration();
        driveMotor.getConfigurator().refresh(t);
        t.MotorOutput.NeutralMode = wantBrake ? NeutralModeValue.Brake : NeutralModeValue.Coast;
        driveMotor.getConfigurator().apply(t);
        angleMotor.getConfigurator().refresh(t);
        t.MotorOutput.NeutralMode = !wantBrake ? NeutralModeValue.Brake : NeutralModeValue.Coast;
        angleMotor.getConfigurator().apply(t);
    }


    public void outputTelemetry() {
        SmartDashboard.putNumber("Module" + moduleNumber + "/Azi Target", targetAngle);
        SmartDashboard.putNumber("Module" + moduleNumber + "/Azi Angle", getCurrentDegrees());
        SmartDashboard.putNumber("Module" + moduleNumber + "/Azi Error", getCurrentDegrees() - targetAngle);
        SmartDashboard.putNumber("Module" + moduleNumber + "/Wheel Velocity", Math.abs(getCurrentVelocity()));
        SmartDashboard.putNumber("Module" + moduleNumber + "/Wheel Target Velocity", Math.abs(targetVelocity));
        SmartDashboard.putNumber("Module" + moduleNumber + "/Drivetrain Position", Math.abs(drivePosition));
        SmartDashboard.putNumber("Module" + moduleNumber + "/Duty Cycle",
                driveMotor.getDutyCycle().getValueAsDouble());
        SmartDashboard.putNumber("Module" + moduleNumber + "/Azi Current",
                angleMotor.getSupplyCurrent().getValueAsDouble());
        SmartDashboard.putNumber("Module" + moduleNumber + "/Drivetrain Current",
                driveMotor.getSupplyCurrent().getValueAsDouble());
        SmartDashboard.putNumber("Module" + moduleNumber + "/Wheel Velocity Error",
                Math.abs(getCurrentVelocity()) - Math.abs(targetVelocity));
    }


    @Override
    public void periodic() {
        drivePosition = driveMotor.getRotorPosition().getValueAsDouble();
        rotationVelocity = angleMotor.getRotorVelocity().getValue();
        driveVelocity = driveMotor.getRotorVelocity().getValue();


        angleMotor.setControl(rotationDemand);
        driveMotor.setControl(driveDemand);
    }

    public static class SwerveModuleConstants {
        public final int driveMotorID;
        public final int angleMotorID;
        public final double angleOffset;

        public SwerveModuleConstants(int driveMotorID, int angleMotorID, double angleOffset) {
            this.driveMotorID = driveMotorID;
            this.angleMotorID = angleMotorID;
            this.angleOffset = angleOffset;
        }
    }

}