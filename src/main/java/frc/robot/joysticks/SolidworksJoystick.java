package frc.robot.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import frc.utils.joysticks.DriveJoystick;
import frc.utils.joysticks.StormXboxController;

public class SolidworksJoystick {

    protected Joystick joystick;
    protected DriveJoystick driveJoystick;
    int port;

    public SolidworksJoystick(int port) {
        this.port = port;
    }

    public double getWpiX() {
        if (driveJoystick != null) {
            return driveJoystick.getWpiXSpeed();
        } else {
            return 0;
        }
    }

    public double getWpiY() {
        if (driveJoystick != null) {
            return driveJoystick.getWpiYSpeed();
        } else {
            return 0;
        }
    }

    public double getOmegaSpeed() {
        if (driveJoystick != null) {
            return driveJoystick.getOmegaSpeed();
        } else {
            return 0;
        }
    }


    public boolean getRobotRelative() {
        return (driveJoystick.getLeftTrigger() > 0.2);
    }

    public double getTurbo() {
        return driveJoystick.getRightTrigger();
    }

    public boolean drivetoBall() {
        return false;
    }

    public boolean relayStart() {
        return false;
    }

    public boolean relayStop() {
        return false;
    }

    public boolean relayOff() {
        return false;
    }

    public boolean shoot() {
        return false;
    }

    public boolean shootBackwards() {
        return false;
    }

    public boolean intake() {
        return false;
    }

    public boolean ballPathForward() {
        return false;
    }

    public boolean ballPathReverse() {
        return false;
    }

    public double getRightTrigger() {
        return 0;
    }
    public double getLeftTrigger() {
        return 0;
    }


}