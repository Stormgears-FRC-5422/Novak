package frc.robot.joysticks;

public class SolidworksDummyController extends SolidworksJoystick {
    SolidworksDummyController(int port) {

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
}

