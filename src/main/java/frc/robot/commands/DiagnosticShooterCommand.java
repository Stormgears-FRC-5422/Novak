package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.joysticks.SolidworksJoystick;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
import frc.utils.joysticks.StormXboxController;

public class DiagnosticShooterCommand extends StormCommand {
    private Shooter shooter;
    private SolidworksJoystick joystick;

    public DiagnosticShooterCommand(Shooter shooter, SolidworksJoystick joystick){
        this.addRequirements(shooter);
        this.shooter = shooter;
        this.joystick = joystick;
    }

    @Override
    public void initialize() {
        shooter.setShooterState(Shooter.ShooterState.ALLISON);

    }

    @Override
    public void execute() {
        double trigger1 = joystick.getLeftTrigger();
        double trigger2 = joystick.getRightTrigger();
        //shooter.setSpeed(trigger1-trigger2);
        shooter.setSpeed(0);

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public void end(boolean interrupted) {
        shooter.setShooterState(Shooter.ShooterState.OFF);

    }

}
