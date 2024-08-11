package frc.robot.subsystems.drive;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule;

import org.ejml.dense.block.MatrixOps_DDRB;

import com.ctre.phoenix6.Utils;

import frc.robot.subsystems.drive.ctrGenerated.CommandSwerveDrivetrain;
import frc.robot.subsystems.drive.ctrGenerated.Telemetry;
import frc.robot.subsystems.drive.ctrGenerated.TunerConstants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import frc.robot.RobotState;

public class CTRDrivetrain extends DrivetrainBase {
    RobotState robotState;
    final CommandSwerveDrivetrain drivetrain;
    final SwerveRequest.ApplyChassisSpeeds drive;
    private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
    private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity
    Telemetry logger = new Telemetry(MaxSpeed);
  
    public CTRDrivetrain() {
        robotState = RobotState.getInstance();

        drivetrain = TunerConstants.DriveTrain;
        drive = new SwerveRequest.ApplyChassisSpeeds()
                .withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage);

        if (Utils.isSimulation()) {
            drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
        }

        setMaxVelocities(MaxSpeed, MaxAngularRate);
        drivetrain.registerTelemetry(logger::telemeterize);
    }

    @Override
    public void periodic() {
        // System.out.println(m_chassisSpeeds.vxMetersPerSecond + " " + m_chassisSpeeds.vyMetersPerSecond + " " + m_chassisSpeeds.omegaRadiansPerSecond);

        drivetrain.applyRequest(() -> drive.withSpeeds(m_chassisSpeeds));
    }
}
