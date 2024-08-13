package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.utils.motorcontrol.StormSpark;
import frc.utils.motorcontrol.StormSpark;

public class Storage extends SubsystemBase {
    public enum StorageState {
        OFF,
        FORWARD,
        REVERSE,
    }

    private final StormSpark storageMotor;
    private double storageMotorSpeed;

    public Storage() {
        storageMotor = new StormSpark(Constants.Storage.storageID, StormSpark.MotorType.kBrushless, StormSpark.MotorKind.k550);
        storageMotor.setInverted(false);
        setStorageState(StorageState.OFF);
    }

    public void periodic() {
        storageMotor.set(storageMotorSpeed);
    }

    public void setStorageState(StorageState state) {
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

    private void setSpeed(double speed) {
        storageMotorSpeed = speed;
    }

}
