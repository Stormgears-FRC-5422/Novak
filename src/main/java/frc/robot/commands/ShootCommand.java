package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
import org.littletonrobotics.junction.AutoLogOutput;
import frc.robot.subsystems.Storage;


public class ShootCommand extends Command {
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
        count = 0;
//        storage.setRelayState(Storage.RelayState.CLOSE);

        shooter.setShooterState(Shooter.ShooterState.FORWARD);
        storage.setStorageState(Storage.StorageState.FORWARD);

        new WaitCommand(1);

        storage.setRelayState(Storage.RelayState.OPEN);

        new WaitCommand(2);

        storage.setRelayState(Storage.RelayState.CLOSE);

    }

    @Override
    public boolean isFinished() {
        return count >= 150;
    }

    @Override
    public void execute() {
        count++;
    }

    public void end(boolean interrupted) {
        shooter.setShooterState(Shooter.ShooterState.OFF);
        storage.setStorageState(Storage.StorageState.OFF);
    }
}
