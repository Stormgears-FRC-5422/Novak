package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
import org.littletonrobotics.junction.AutoLogOutput;

public class Shoot extends Command {
    @AutoLogOutput(key = "shooter_speed")
    Shooter shooter;
    double count;
    Storage storage;
    public Shoot(Shooter shooter, Storage storage) {
        this.shooter = shooter;
        this.storage = storage;
        addRequirements(shooter, storage);

    }

    @Override
    public void initialize() {
        count =0;
    }

    @Override
    public boolean isFinished() {
        return count >= 100;
    }

    @Override
    public void execute() {
        shooter.setSpeed(0.15);
        storage.setSpeed(-0.5);
        count++;
        
    }
    public void end(boolean interrupted) {
        shooter.setSpeed(0);
        storage.setSpeed(0);
      }

    
}
