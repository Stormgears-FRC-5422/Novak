package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pigeon extends SubsystemBase {
    Pigeon2 pigeon;
    public Pigeon (){
        pigeon = new Pigeon2(0);
    }

    public double getYaw(){
        return pigeon.getYaw().getValue();
    }

    @Override
    public void periodic() {
        super.periodic();
        System.out.println(getYaw());
    }
}