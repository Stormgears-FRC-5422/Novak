// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.utils.motorcontrol.StormSpark;

public class Intake extends SubsystemBase {
  public enum IntakeState {
    OFF,
    FORWARD,
    REVERSE,
  }

  private final StormSpark intakeMotor;
  private double intakeMotorSpeed;

  /** Creates a new Intake. */
  public Intake() {
    // used fake id, change to real motor id later
    intakeMotor = new StormSpark(Constants.Intake.intakeID, CANSparkLowLevel.MotorType.kBrushless, StormSpark.MotorKind.k550);
    intakeMotor.setInverted(true);
    setIntakeState(IntakeState.OFF);
  }

  @Override
  public void periodic() {
    intakeMotor.set(intakeMotorSpeed);
  }

  public void setIntakeState(IntakeState state) {
    switch (state) {
      case OFF -> {
        setSpeed(0.0);
      }
      case FORWARD -> {
        setSpeed(Constants.Intake.intakeSpeed);
      }
      case REVERSE ->{
        setSpeed(-Constants.Intake.intakeSpeed);
      }
    }
  }

  private void setSpeed(double speed) {
    intakeMotorSpeed = speed;
  }
}
