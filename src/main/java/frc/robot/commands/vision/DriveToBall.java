package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.VisionSubsystem;

public class DriveToBall extends Command {
    VisionSubsystem visionSubsystem;
    public DriveToBall(VisionSubsystem visionSubsystem) {
        this.visionSubsystem = visionSubsystem;
        addRequirements(visionSubsystem);
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
        //need to add if sensor detects the ball coming in
    }
}
