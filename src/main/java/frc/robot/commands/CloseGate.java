package frc.robot.commands;
import frc.robot.subsystems.Storage;
import edu.wpi.first.wpilibj2.command.Command;


public class CloseGate extends Command {
    private final Storage storageSubsystem;

    public CloseGate(Storage storageSubsystem) {
        this.storageSubsystem = storageSubsystem;
        addRequirements(storageSubsystem);
    }

    @Override
    public void initialize() {
        storageSubsystem.setRelayState(Storage.RelayState.OPEN);
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
