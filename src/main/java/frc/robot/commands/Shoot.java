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
        shooter.setShooterState(Shooter.shooterState.REVERSE);
        storage.setStorageState(Storage.StorageState.REVERSE);

    }

    @Override
    public boolean isFinished() {
        return count >= 100;
    }

    @Override
    public void execute() {
        count++;
        
    }
    public void end(boolean interrupted) {
        shooter.setShooterState(Shooter.shooterState.OFF);
        storage.setStorageState(Storage.StorageState.OFF);
        storage.setSpeed(0);
      }

    
}
