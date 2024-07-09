package frc.robot.commands.vision;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.VisionSubsystem;
import org.littletonrobotics.junction.Logger;
import frc.robot.Constants;

public class DriveToBall extends Command {
    VisionSubsystem visionSubsystem;
    private static int TARGET =  -Constants.DriveToBall.targetOffset;
    private final PIDController translationController = new PIDController(Constants.DriveToBall.translationKp,
            Constants.DriveToBall.translationKi, Constants.DriveToBall.translationKd);
    private final PIDController rotationController = new PIDController(Constants.DriveToBall.rotationKp,
            Constants.DriveToBall.rotationKi, Constants.DriveToBall.rotationKd);
    //keeping values same for now but will change with testing

    Drivetrain drivetrain;
    int count = 0;
    double movement = 0;
    double rotation = 0;
    double tx;
    double ty;





    public DriveToBall(Drivetrain drivetrain, VisionSubsystem visionSubsystem) {
        this.visionSubsystem = visionSubsystem;
        this.drivetrain = drivetrain;
        addRequirements(visionSubsystem, drivetrain);
    }

    @Override
    public void initialize() {
        count = 0;
        movement = 0;
        rotation = 0;
        translationController.setSetpoint(0.0);
        rotationController.setSetpoint(0.0);
    }
    @Override
    public void execute() {
        rotation = 0;
        if (visionSubsystem.getTennisBall().isPresent()) {
            tx = visionSubsystem.getTennisBall().get().tx;
            ty = visionSubsystem.getTennisBall().get().ty;
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
        return count>10;
        //need to add if sensor detects the ball coming in
    }
    @Override
    public void end(boolean interrupted) {
        drivetrain.drive(new ChassisSpeeds(0,0,0), false);
    }
}
