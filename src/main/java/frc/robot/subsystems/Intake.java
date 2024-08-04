// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  public enum IntakeState {
    OFF,
    FORWARD,
    REVERSE,
  }

  private final CANSparkMax intakeMotor;
  private double intakeMotorSpeed;
  private IntakeState intakeState;

  /** Creates a new Intake. */
  public Intake() {
    // used fake id, change to real motor id later
    intakeMotor = new CANSparkMax(Constants.Intake.intakeID, CANSparkLowLevel.MotorType.kBrushless);
    intakeMotor.setInverted(true);
    setIntakeState(IntakeState.OFF);
  }

  public void setSpeed(double speed) {
    intakeMotorSpeed = speed;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    intakeMotor.set(intakeMotorSpeed);



  }

  public void setIntakeState(IntakeState state) {
    this.intakeState = state;
    switch (state) {
      case OFF -> {
        setSpeed(0.0);
      }
      case FORWARD -> {
        setSpeed(Constants.Intake.intakeSpeed);
      }
      case REVERSE ->{
        setSpeed(Constants.Intake.intakeSpeed * -1);
      }
    }
  }



}
