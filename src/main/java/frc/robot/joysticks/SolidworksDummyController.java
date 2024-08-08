package frc.robot.joysticks;

public class SolidworksDummyController extends SolidworksJoystick {
    SolidworksDummyController(int port) {
        super(0);

    }

    @Override
    public double getWpiX() {
        return 0;
    }

    @Override
    public double getWpiY() {
        return 0;
    }

    @Override
    public double getOmegaSpeed() {
        return 0;
    }

    @Override
    public boolean getRobotRelative() {
        return false;
    }

    @Override
    public double getTurbo() {
        return 0;
    }

    @Override
    public boolean drivetoBall() {
        return false;
    }

    @Override
    public boolean intake() { return false; }

    @Override
    public boolean relayStart() {
        return false;
    }
    @Override
    public boolean relayStop() {
        return false;
    }
    @Override
    public boolean relayOff() {
        return false;
    }

    @Override
    public boolean shoot() {
        return false;
    }
}


