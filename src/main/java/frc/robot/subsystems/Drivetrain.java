package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotState;
import frc.utils.swerve.SwerveConstants;
import frc.utils.swerve.SwerveModule;

public class Drivetrain extends SubsystemBase {

    RobotState robotState = new RobotState();

    private final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(
            // Front left
            new Translation2d(Constants.Drive.drivetrainWheelbaseMeters / 2.0, Constants.Drive.drivetrainTrackwidthMeters / 2.0),
            // Front right
            new Translation2d(Constants.Drive.drivetrainWheelbaseMeters / 2.0, -Constants.Drive.drivetrainTrackwidthMeters / 2.0),
            // Back left
            new Translation2d(-Constants.Drive.drivetrainWheelbaseMeters / 2.0, Constants.Drive.drivetrainTrackwidthMeters / 2.0),
            // Back right
            new Translation2d(-Constants.Drive.drivetrainWheelbaseMeters / 2.0, -Constants.Drive.drivetrainTrackwidthMeters / 2.0)
    );

    private final SwerveModule m_frontLeftModule;
    private final SwerveModule m_frontRightModule;
    private final SwerveModule m_backLeftModule;
    private final SwerveModule m_backRightModule;

    private double m_driveSpeedScale = 1;


    double maxVelocityMetersPerSecond = Constants.Drive.FreeSpeedRPM / 60.0 *
            Constants.Drive.wheelDiameter * Math.PI;
    double maxAngularVelocityRadiansPerSecond = maxVelocityMetersPerSecond /
            Math.hypot(Constants.Drive.drivetrainTrackwidthMeters / 2.0, Constants.Drive.drivetrainWheelbaseMeters / 2.0);


    private boolean m_fieldRelative = false;
    private ChassisSpeeds m_chassisSpeeds = new ChassisSpeeds(0.0, 0.0, 0.0);


    SwerveModuleState[] states = m_kinematics.toSwerveModuleStates(m_chassisSpeeds);


    public Drivetrain() {
        m_frontLeftModule = new SwerveModule(1, new SwerveModule.SwerveModuleConstants(1, 2, Constants.Drive.frontRightOffsetDegrees),
                SwerveConstants.mFrontLeftCancoder);
        m_frontRightModule = new SwerveModule(2, new SwerveModule.SwerveModuleConstants(3, 4, Constants.Drive.frontLeftOffsetDegrees),
                SwerveConstants.mFrontRightCancoder);
        m_backRightModule = new SwerveModule(3, new SwerveModule.SwerveModuleConstants(5, 6, Constants.Drive.backLeftOffsetDegrees),
                SwerveConstants.mBackRightCancoder);
        m_backLeftModule = new SwerveModule(4, new SwerveModule.SwerveModuleConstants(7, 8, Constants.Drive.backRightOffsetDegrees),
                SwerveConstants.mBackLeftCancoder);

    }
    public void setDriveSpeedScale(double scale) {
        m_driveSpeedScale = MathUtil.clamp(scale, 0, Constants.Drive.driveSpeedScale);
    }



    public void drive(ChassisSpeeds speeds, boolean fieldRelative, double speedScale) {
        m_fieldRelative = fieldRelative;

        if (fieldRelative) {
            Rotation2d rotation = robotState.getHeading();
            m_chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(speeds, rotation);
        } else {
            m_chassisSpeeds = speeds;
        }

        // TODO - work in the slew rate limiter. Apply before scale to preserve motion details
        m_chassisSpeeds = scaleChassisSpeeds(m_chassisSpeeds, speedScale);

    }

    public ChassisSpeeds scaleChassisSpeeds(ChassisSpeeds speeds, double scale) {
        return new ChassisSpeeds(scale * speeds.vxMetersPerSecond,
                scale * speeds.vyMetersPerSecond,
                scale * speeds.omegaRadiansPerSecond);
    }

    public void drive(ChassisSpeeds speeds, boolean fieldRelative) {
        drive(speeds, fieldRelative, m_driveSpeedScale);
    }

    /**
     * Command the robot to drive, especially from Joystick
     * This method expects units from -1 to 1, and then scales them to the max speeds
     * You should call setMaxVelocities() before calling this method
     *
     * @param speeds        Chassis speeds, especially from joystick.
     * @param fieldRelative True for field relative driving
     */
    public void percentOutputDrive(ChassisSpeeds speeds, boolean fieldRelative) {
        drive(new ChassisSpeeds(speeds.vxMetersPerSecond * maxVelocityMetersPerSecond,
                        speeds.vyMetersPerSecond * maxVelocityMetersPerSecond,
                        speeds.omegaRadiansPerSecond * maxAngularVelocityRadiansPerSecond),
                fieldRelative);
    }

    @Override
    public void periodic() {
        states = m_kinematics.toSwerveModuleStates(m_chassisSpeeds);

        m_frontLeftModule.setVelocity(states[0]);
        m_frontRightModule.setVelocity(states[1]);
        m_backRightModule.setVelocity(states[2]);
        m_backLeftModule.setVelocity(states[3]);

    }
}
