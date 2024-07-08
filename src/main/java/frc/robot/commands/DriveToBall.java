package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.VisionSubsystem;

public class DriveToBall extends Command {
    VisionSubsystem visionSubsystem;
    public DriveToBall(VisionSubsystem visionSubsystem) {
        this.visionSubsystem = visionSubsystem;
    }
}
