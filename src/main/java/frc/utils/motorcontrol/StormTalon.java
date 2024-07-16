package frc.utils.motorcontrol;

import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.hardware.TalonFX;

public class StormTalon extends TalonFX implements MotorController {

    private ControlRequest rotationDemand;
    private ControlRequest driveDemand;


    public StormTalon(int deviceNumber) {
        super(deviceNumber);
    }


    @Override
    public void setVelocity(double velocity) {

    }

    @Override
    public void setAngle(double angle) {

    }

    @Override
    public void restoretoFactoryDefaults() {

    }

    @Override
    public void setMotorPosition(double position) {

    }

    @Override
    public void applyConfig() {

    }

    @Override
    public void getAnglePosition() {

    }

}
