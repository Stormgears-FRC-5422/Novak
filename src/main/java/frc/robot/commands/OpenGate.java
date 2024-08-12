package frc.robot.commands;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;

public class OpenGate extends StormCommand {
    private final Shooter shooter;

    public OpenGate(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        super.initialize();

        shooter.setShooterState(Shooter.ShooterState.GATE_ONLY);
    }
}
