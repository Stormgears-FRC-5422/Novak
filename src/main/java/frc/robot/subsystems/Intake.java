// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;

public class Intake extends SubsystemBase {
  private final CANSparkMax intakeMotor;

  /** Creates a new Intake. */
  public Intake() {
    // used fake id, change to real motor id later
    intakeMotor = new CANSparkMax(3, CANSparkLowLevel.MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setSpeed(double speed) {
    intakeMotor.set(speed);
  }

}
