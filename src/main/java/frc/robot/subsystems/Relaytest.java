package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Relaytest extends SubsystemBase {

    private Relay LinearActuator;

    public Relaytest() {
        LinearActuator = new Relay(1);
    }

    public void startActuator() {
        System.out.println("pressed");
        LinearActuator.set(Relay.Value.kForward);
    }

    public void offActuator() {
        LinearActuator.set(Relay.Value.kOff);

    }
    public void stopActuator() {
        LinearActuator.set(Relay.Value.kReverse);
    }
}
