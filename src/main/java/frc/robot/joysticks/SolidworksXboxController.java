package frc.robot.joysticks;

import frc.utils.joysticks.StormXboxController;

public class SolidworksXboxController extends SolidworksJoystick {
    StormXboxController controller;

    SolidworksXboxController(int port) {
        controller = new StormXboxController(port);
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
        return controller.getLeftTrigger() > 0.2;
    }

    public double getTurbo() {
        return controller.getRightTrigger();
    }



}