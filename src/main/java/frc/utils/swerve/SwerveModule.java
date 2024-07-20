package frc.utils.swerve;

import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.*;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Constants;
import frc.utils.Conversions;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

import static frc.utils.Conversions.MPSToRPS;


public class SwerveModule extends SubsystemBase {
    private final int moduleNumber;
    private final double angleOffset;

    private TalonFX angleMotor;
    private TalonFX driveMotor;
    private CANcoder angleEncoder;
    @AutoLogOutput
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

        angleMotor.getConfigurator().apply(SwerveConstants.AngleFXConfig());
        driveMotor.getConfigurator().apply(SwerveConstants.DriveFXConfig());

        driveMotor.setPosition(0);

        resetToAbsolute();

    }


    public void resetToAbsolute() {
        double angle = placeInAppropriate0To360Scope(getCurrentDegrees(), getAbsolutePosition() - angleOffset);
        double absPosition = Conversions.degreesToRotation(angle, Constants.Drive.angleGearRatio);
        angleMotor.setPosition(absPosition);
    }

    @AutoLogOutput(key = "Module {moduleNumber}/absolute angle")
    public double getAbsolutePosition() {
        return angleEncoder.getAbsolutePosition().getValue() * 360;
    }

    private double placeInAppropriate0To360Scope(double scopeReference, double newAngle) {
        double lowerBound;
        double upperBound;
        double lowerOffset = scopeReference % 360;
        if (lowerOffset >= 0) {
            lowerBound = scopeReference - lowerOffset;
            upperBound = scopeReference + (360 - lowerOffset);
        } else {
            upperBound = scopeReference - lowerOffset;
            lowerBound = scopeReference - (360 + lowerOffset);
        }
        while (newAngle < lowerBound) {
            newAngle += 360;
        }
        while (newAngle > upperBound) {
            newAngle -= 360;
        }
        if (newAngle - scopeReference > 180) {
            newAngle -= 360;
        } else if (newAngle - scopeReference < -180) {
            newAngle += 360;
        }
        return newAngle;
    }
    @AutoLogOutput(key = "Module {moduleNumber}/angle")
    public double getCurrentDegrees() {
        return Conversions.rotationsToDegrees(angleMotor.getRotorPosition().getValue(), Constants.Drive.angleGearRatio);
    }

    public void setVelocity(SwerveModuleState desiredState) {
        Logger.recordOutput("Module" + moduleNumber + " desired angle", desiredState.angle.getDegrees());
        double flip = setSteeringAngleOptimized(desiredState.angle) ? -1 : 1;
        setSteeringAngleRaw(desiredState.angle.getDegrees());
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
//        System.out.println(steerAngle.getDegrees());
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

//    private boolean flipHeading(Rotation2d prevToGoal) {
//        return Math.abs(prevToGoal.getRadians()) > Math.PI / 2.0;
//    }


    private void setSteeringAngleRaw(double angleDegrees) {
//        System.out.println(angleDegrees);
        double rotorPosition = Conversions.degreesToRotation(angleDegrees, Constants.Drive.angleGearRatio);

//        System.out.println(rotorPosition);
//        PositionVoltage positionVoltage = new PositionVoltage(rotorPosition);
        rotationDemand = new PositionDutyCycle(rotorPosition, 0.0, false, 0.0, 0, false, false, false);
//        rotationDemand = positionVoltage;
//        rotationDemand = new PositionDutyCycle(angleDegrees, 0.0, false, 0.0, 0, false, false, false);
    }
    @AutoLogOutput(key = "Module {moduleNumber}/velocity")
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

//        Logger.recordOutput("Module" + moduleNumber + " rotor position", rotorPosition);


        angleMotor.setControl(rotationDemand);
//        angleMotor.setPosition()
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