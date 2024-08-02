package frc.robot.joysticks;

import frc.utils.joysticks.StormLogitechController;

public class SolidworksLogitechController extends SolidworksJoystick {
    StormLogitechController controller;

    SolidworksLogitechController(int port) {
        controller = new StormLogitechController(port);
    }


    public double getWpiX() {
        return controller.getWpiXSpeed();
    }

    public double getWpiY() {
        return controller.getWpiYSpeed();
    }

    public double getOmegaSpeed() {
        return controller.getOmegaSpeed();
    }

    public boolean getRobotRelative() {
//        return controller.getRawButton(11);
        return false;

    }

    public double getTurbo() {
        return 1;
    }

    @Override
    public boolean drivetoBall() {
        return false;
    }

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
}