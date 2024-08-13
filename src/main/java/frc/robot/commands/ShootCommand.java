package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
import org.littletonrobotics.junction.AutoLogOutput;
import frc.robot.subsystems.Storage;

public class ShootCommand extends StormCommand {
    @AutoLogOutput(key = "shooter_speed")
    Shooter shooter;
    Storage storage;
    double count;

    public ShootCommand(Shooter shooter, Storage storage) {
        this.shooter = shooter;
        this.storage = storage;
        addRequirements(shooter, storage);
    }

    @Override
    public void initialize() {
        super.initialize();

        count = 0;
        shooter.setShooterState(Shooter.ShooterState.WARMUP);
        storage.setStorageState(Storage.StorageState.OFF);
    }

    @Override
    public void execute() {
        count++;
        if (count > 50) {
            shooter.setShooterState(Shooter.ShooterState.FORWARD);
            storage.setStorageState(Storage.StorageState.FORWARD);
        }
    }

    @Override
    public boolean isFinished() {
        return count >= Constants.Shooter.maxRunTime;
    }

    public void end(boolean interrupted) {
        shooter.setShooterState(Shooter.ShooterState.OFF);
        storage.setStorageState(Storage.StorageState.OFF);
    }
}
