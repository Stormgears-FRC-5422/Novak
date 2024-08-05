package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import org.littletonrobotics.junction.Logger;

public class Shoot extends Command {
    Shooter shooter;
    double count;
    public Shoot(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);

    }

    @Override
    public void initialize() {
        count =0;
    }

    @Override
    public boolean isFinished() {
        return count == 100;
    }

    @Override
    public void execute() {
        shooter.setSpeed(0.3);
        count++;
        if(count%4==0){
            Logger.recordOutput("Shooter Applied speed:", shooter.getAppliedOutput());
        }
    }
    public void end(boolean interrupted) {
        shooter.setSpeed(0);
      }

    
}
