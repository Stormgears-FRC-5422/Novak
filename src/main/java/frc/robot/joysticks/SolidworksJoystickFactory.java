package frc.robot.joysticks;

public class SolidworksJoystickFactory {
    public static SolidworksJoystick instance;

    public static SolidworksJoystick getInstance(String joystickType, int port) throws IllegalJoystickTypeException {
        System.out.println("Initializing " + joystickType + " as Joystick");
        switch (joystickType.toLowerCase()) {
            case "xboxcontroller" -> instance = new SolidworksXboxController(port);
            case "logitechcontroller" -> instance = new SolidworksLogitechController(port);
            case "dummy" -> instance = new SolidworksDummyController(port);
            default -> throw new IllegalJoystickTypeException("Illegal Joystick Type: " + joystickType + " ---!");
        }
        return instance;
    }
}