package frc.robot.commands.vision;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotState;
import frc.robot.joysticks.SolidworksXboxController;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.DrivetrainBase;
import frc.robot.subsystems.VisionSubsystem;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;

public class DriveToBall extends Command {
    /*
    VisionSubsystem visionSubsystem;
    private static int TARGET =  -Constants.DriveToBall.targetOffset;
    private final PIDController translationController = new PIDController(Constants.DriveToBall.translationKp,
            Constants.DriveToBall.translationKi, Constants.DriveToBall.translationKd);
    private final PIDController rotationController = new PIDController(Constants.DriveToBall.rotationKp,
            Constants.DriveToBall.rotationKi, Constants.DriveToBall.rotationKd);
    //keeping values same for now but will change with testing

    DrivetrainBase drivetrain;
    int count = 0;
    double movement = 0;
    double rotation = 0;
    @AutoLogOutput(key = "Vision/DriveToBall/tx")
    double tx;
    @AutoLogOutput(key = "Vision/DriveToBall/ty")
    double ty;
    RobotState robotState;




    public DriveToBall(DrivetrainBase drivetrain, VisionSubsystem visionSubsystem) {
        this.visionSubsystem = visionSubsystem;
        this.drivetrain = drivetrain;
        robotState = RobotState.getInstance();
        addRequirements(visionSubsystem, drivetrain);
    }

    @Override
    public void initialize() {
        System.out.println("Drive ball init");
        count = 0;
        movement = 0;
        rotation = 0;
        translationController.setSetpoint(0.0);
        rotationController.setSetpoint(0.0);
    }
    @Override
    public void execute() {
        visionSubsystem.changeToDetectorPipeline();
        tx = LimelightHelpers.getTX("");
        ty = LimelightHelpers.getTY("");
        if (!LimelightHelpers.getTV("")) {
            return;
        }else {
            movement = translationController.calculate(TARGET - ty);
            rotation = rotationController.calculate(tx);

        }

        if (visionSubsystem.getTennisBall().isPresent()) {
            System.out.println("tenny ball pres");
            if (tx > 0) {
                robotState.setVisionState(RobotState.VisionState.RIGHT);
            } else if (tx < 0) {
                robotState.setVisionState(RobotState.VisionState.LEFT);
            }
           // tx = visionSubsystem.getTennisBall().get().tx;
           // ty = visionSubsystem.getTennisBall().get().ty;

//            System.out.println("Note detected");
            count = 0;
        } else {
            System.out.println("tenny ball no pres");
            count++;
            robotState.setVisionState(RobotState.VisionState.IDLE);
        }
        System.out.println("Movement: " + movement);
        System.out.println("ROT: " + rotation);
        System.out.println("count: " + count);
        ChassisSpeeds speeds = new ChassisSpeeds(movement, 0, rotation);
        drivetrain.drive(speeds, false);
        //drivetrain.drive(speeds, false, 1);
        Logger.recordOutput("Tx" + tx);
        Logger.recordOutput("Ty" + ty);

    }
    @Override
    public boolean isFinished() {
        return count>50;
        //need to add if sensor detects the ball coming in
    }
    @Override
    public void end(boolean interrupted) {

        ChassisSpeeds speeds;
        speeds = new ChassisSpeeds(0, 0, 0);
        drivetrain.percentOutputDrive(speeds, false);
    }

     */
    private static int TARGET =  -Constants.DriveToBall.targetOffset;
    private final PIDController translationController = new PIDController(Constants.DriveToBall.translationKp,
            Constants.DriveToBall.translationKi, Constants.DriveToBall.translationKd);
    private final PIDController rotationController = new PIDController(Constants.DriveToBall.rotationKp,
            Constants.DriveToBall.rotationKi, Constants.DriveToBall.rotationKd);

    DrivetrainBase drivetrain;
    SolidworksXboxController controller;
    double tx;
    double ty;
    NetworkTable table;
    VisionSubsystem visionSubsystem;
    int count = 0;
    double movement = 0;
    double rotation = 0;

    public DriveToBall(DrivetrainBase drivetrain, VisionSubsystem visionSubsystem) {
        translationController.setSetpoint(0.0);
        rotationController.setSetpoint(0.0);
        this.drivetrain = drivetrain;
        this.visionSubsystem = visionSubsystem;
        addRequirements(drivetrain, visionSubsystem);
        System.out.println("DriveToBall() working");
    }

    @Override
    public void initialize() {
        count = 0;
        movement = 0;
        rotation = 0;
        translationController.setSetpoint(0.0);
        rotationController.setSetpoint(0.0);
        System.out.println("DriveToBall::initialize() working");
    }

    @Override
    public void execute() {
        rotation = 0;
        if (LimelightHelpers.getTV("")) {
            tx = LimelightHelpers.getTX("");
            ty = LimelightHelpers.getTY("");
            movement = translationController.calculate(TARGET - ty);
            rotation = rotationController.calculate(tx);
//            System.out.println("Note detected");
            count = 0;
        } else {
            count++;
        }
        System.out.println("Movement: " + movement);
        System.out.println("ROT: " + rotation);
        System.out.println("count: " + count);
        ChassisSpeeds speeds = new ChassisSpeeds(movement, 0, rotation);
        drivetrain.drive(speeds, false, 1);
        Logger.recordOutput("Tx" + tx);
        Logger.recordOutput("Ty" + ty);

    }


    @Override
    public boolean isFinished() {
//        return RobotState.getInstance().getShooterState() == Shooter.ShooterState.STAGED_FOR_SHOOTING;
        return count > 10;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Tx: " + tx);
        System.out.println("Ty: " + ty);
        ChassisSpeeds speeds;
        speeds = new ChassisSpeeds(0, 0, 0);
        drivetrain.percentOutputDrive(speeds, false);
        System.out.println("Drive To Note Finished: interrupted:" + interrupted);
    }
}

