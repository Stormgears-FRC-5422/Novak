package frc.robot.joysticks;

import static java.util.Objects.isNull;

public class SolidworksJoystickFactory {
    public static SolidworksJoystick instance;

    public static SolidworksJoystick getInstance(String joystickType, int port) throws IllegalJoystickTypeException {
        System.out.println("Initializing " + joystickType + " as Joystick");
        if (isNull(instance)) {
            switch (joystickType.toLowerCase()) {
                case "xboxcontroller" -> instance = new SolidworksXboxController(port);
                case "logitechcontroller" -> instance = new SolidworksLogitechController(port);
                case "dummy" -> instance = new SolidworksDummyController(port);
                default -> throw new IllegalJoystickTypeException("Illegal Joystick Type: " + joystickType + " ---!");
            }
        }

        return instance;
    }
}