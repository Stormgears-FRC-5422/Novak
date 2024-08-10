package frc.robot.joysticks;

import frc.utils.joysticks.StormLogitechController;

public class SolidworksLogitechController extends SolidworksJoystick {
    StormLogitechController controller;

    SolidworksLogitechController(int port) {
        super(0);
        controller = new StormLogitechController(port);
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

    public boolean intake() { return controller.getRawButton(2); }

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
        return controller.getRawButton(1);
    }

    public boolean ballPathForward() { return controller.getRawButton(5);}
    public boolean ballPathReverse() { return controller.getRawButton(6);}
}