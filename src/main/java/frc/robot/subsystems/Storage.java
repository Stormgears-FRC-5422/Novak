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

    public enum RelayState {
        IDLE,
        CLOSE,
        OPEN

    }



    private final CANSparkMax storageMotor;
    private double storageMotorSpeed;

    private double voltage;
    private StorageState storageState;

    private RelayState relaystate;

    private final CANSparkMax relayspark;

    private int m_currentLimit;

    public Storage() {
        storageMotor = new StormSpark(Constants.Storage.storageID, StormSpark.MotorType.kBrushed, StormSpark.MotorKind.kNeo);
        storageMotor.setInverted(true);
        setStorageState(StorageState.OFF);
        relayspark = new CANSparkMax(Constants.RelayID, CANSparkLowLevel.MotorType.kBrushed);
        m_currentLimit = Constants.CurrentLimitAmps;
        relayspark.setSmartCurrentLimit(m_currentLimit);

        voltage = 0.0;

    }

    public void setVoltage(double outputVolts) {
        this.voltage = outputVolts;
    }


    public void setSpeed(double speed) {
        storageMotorSpeed = speed;
    }


    public void periodic() {
        storageMotor.set(storageMotorSpeed);
        relayspark.setVoltage(voltage);
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

    public void setRelayState(RelayState state) {
        this.relaystate = state;
        switch (state) {
            case IDLE -> {
                System.out.println("gate neutralized");

                setVoltage(0.0);
            }
            case CLOSE -> {
                System.out.println("closed gate");

                setVoltage(Constants.Storage.Close);

            }
            case OPEN -> {
                System.out.println("gate opened");

                setVoltage(Constants.Storage.Open);

//                new WaitCommand(3.0);



            }
        }
    }
}
