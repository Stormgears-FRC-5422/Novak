package frc.utils.motorcontrol;

public interface MotorController {
    void set(double speed);

    void setInverted(boolean inverted);

    void setVelocity(double velocity);

    void setAngle(double angle);

//    void setSmartCurrentLimit(int currentLimit);

    void restoretoFactoryDefaults();

    void setMotorPosition(double position);

    void applyConfig();

    void getAnglePosition();






}
