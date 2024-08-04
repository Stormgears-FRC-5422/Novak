package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Storage extends SubsystemBase {
    private final CANSparkMax storageMotor;
    private double speed;
    public Storage (){
        storageMotor = new CANSparkMax(Constants.Storage.storageID, CANSparkLowLevel.MotorType.kBrushless);
        storageMotor.setInverted(true);
    }
    public void periodic(){
        storageMotor.set(speed);
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }
}
