package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.VisionSubsystem;

public class TurnToNextBall extends Command {
    VisionSubsystem visionSubsystem;
    public TurnToNextBall(VisionSubsystem visionSubsystem) {
        this.visionSubsystem = visionSubsystem;
    }

    @Override
    public void execute() {
        //turn clockwise to next ball

    }

    @Override
    public boolean isFinished() {
        return visionSubsystem.getTennisBall().isPresent();
    }
}
