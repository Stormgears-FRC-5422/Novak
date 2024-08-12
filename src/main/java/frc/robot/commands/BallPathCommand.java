package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.Shooter;

public class BallPathCommand extends StormCommand {
    /** Creates a new Intake. */
    Intake intake;
    Storage storage;
    Shooter shooter;

    private int counter;
    private boolean forward;

    public BallPathCommand(Intake intake, Storage storage, Shooter shooter, boolean forward) {
        this.intake = intake;
        this.storage = storage;
        this.shooter = shooter;
        this.forward = forward;
        addRequirements(intake, storage, shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        super.initialize();

        intake.setIntakeState(forward ? Intake.IntakeState.FORWARD : Intake.IntakeState.REVERSE);
        storage.setStorageState(forward ? Storage.StorageState.FORWARD : Storage.StorageState.REVERSE);
        shooter.setShooterState(forward ? Shooter.ShooterState.FORWARD : Shooter.ShooterState.REVERSE);
        counter = 0;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        counter++;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intake.setIntakeState(Intake.IntakeState.OFF);
        storage.setStorageState(Storage.StorageState.OFF);
        shooter.setShooterState(Shooter.ShooterState.OFF);
        super.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return counter >= 5000;
    }
}
