package frc.robot.joysticks;

public abstract class SolidworksJoystick {
    public abstract double getWpiX();

    public abstract double getWpiY();

    public abstract double getOmegaSpeed();

    public abstract boolean getRobotRelative();

    public abstract double getTurbo();

    public abstract boolean drivetoBall();

    public abstract boolean intake();
}