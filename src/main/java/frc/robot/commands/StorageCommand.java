package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Storage;

public class StorageCommand extends Command {
    Storage storage;
    public StorageCommand (Storage storage){
        this.storage = storage;
    }

    @Override
    public void execute() {
        storage.setSpeed(-0.5);
    }

    @Override
    public void end(boolean interrupted) {
        storage.setSpeed(0);
    }
}
