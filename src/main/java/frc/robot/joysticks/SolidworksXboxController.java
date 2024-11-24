package frc.robot.joysticks;

import frc.utils.joysticks.StormXboxController;

public class SolidworksXboxController extends SolidworksJoystick {
    StormXboxController controller;

    public SolidworksXboxController(int port) {
        super(port);
        controller = new StormXboxController(port);
        this.joystick = controller;
        this.driveJoystick = controller;
    }

    public boolean getRobotRelative() {
        return controller.getLeftTrigger() > 0.2;
    }

    public double getTurbo() {
        return controller.getRightTrigger();
    }

    @Override
    public boolean drivetoBall() {
        return controller.getLeftLittleButtonIsHeld();
    }
    @Override
    public boolean intake() {
        return controller.getBButtonIsHeld();
    }

    @Override
    public boolean relayStart() {
        return controller.getXisPressed();

    }

    @Override
    public boolean relayStop() {
        return controller.getAButtonIsHeld();
    }

    @Override
    public boolean relayOff() {
        return controller.getYButtonIsHeld();
    }

    @Override
    public boolean shoot() {
        return controller.getXButtonIsHeld();
    }

    public boolean shootBackwards() {
        return controller.getYButtonIsHeld();
    }

    public double getRightTrigger() {
        return controller.getRightTrigger();
    }
    public double getLeftTrigger() {
        return controller.getLeftTrigger();
    }

}