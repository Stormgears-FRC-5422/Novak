package frc.robot.commands;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pigeon;

public class PigeonCommand extends StormCommand {
    private final Pigeon pigeon;
    private int counter;

    public PigeonCommand(Pigeon pigeon) {
        this.pigeon = pigeon;
        addRequirements(pigeon);
    }


    @Override
    public void initialize() {
        super.initialize();
        counter = 0;
    }

    @Override
    public void execute() {
        counter++;
        if (counter > 250) {
            pigeon.pigeonReset();
            counter = 0;
        }
    }
}

