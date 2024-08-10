package frc.robot.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.JoyStickDrive;
import frc.utils.joysticks.DriveJoystick;
import frc.utils.joysticks.StormXboxController;
import frc.robot.joysticks.SolidworksJoystick;

public class SolidworksXboxController extends SolidworksJoystick {
    StormXboxController controller;

    public SolidworksXboxController(int port) {
        super(0);
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
        return controller.getYButtonIsHeld();
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
        return controller.getDownArrowPressed();
    }
}