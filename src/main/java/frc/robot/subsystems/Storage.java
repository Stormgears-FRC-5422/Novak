package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



public class Storage extends SubsystemBase {
    public enum StorageState {
        OFF,
        FORWARD,
        REVERSE,
    }

    final CANSparkMax storageMotor;
    double storageMotorSpeed;
    StorageState storageState;

    public Storage() {
        storageMotor = new CANSparkMax(Constants.Storage.storageID, CANSparkLowLevel.MotorType.kBrushless);
        storageMotor.setInverted(false);
        setStorageState(StorageState.OFF);
    }

    void setSpeed(double speed) {
        storageMotorSpeed = speed;
    }

    public void periodic() {
        storageMotor.set(storageMotorSpeed);
    }

    public void setStorageState(StorageState state) {
        this.storageState = state;
        switch (state) {
            case OFF -> {
                setSpeed(0.0);
            }
            case FORWARD -> {
                setSpeed(Constants.Storage.storageSpeed);
            }
            case REVERSE -> {
                setSpeed(-Constants.Storage.storageSpeed);
            }
        }
    }
}
