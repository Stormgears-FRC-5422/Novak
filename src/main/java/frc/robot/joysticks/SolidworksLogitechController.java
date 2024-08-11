package frc.robot.joysticks;

import frc.utils.joysticks.StormLogitechController;

public class SolidworksLogitechController extends SolidworksJoystick {
    StormLogitechController controller;

    SolidworksLogitechController(int port) {
        super(port);
        controller = new StormLogitechController(port);
        this.joystick = controller;
        this.driveJoystick = controller;
    }

    @Override
    public boolean intake() {
        return controller.getRawButton(2);
    }

    @Override
    public boolean shoot() {
        return controller.getRawButton(1);
    }

    @Override
    public boolean ballPathForward() {
        return controller.getRawButton(5);
    }

    @Override
    public boolean ballPathReverse() {
        return controller.getRawButton(6);
    }
}