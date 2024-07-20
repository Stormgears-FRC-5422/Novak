// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.utils.LoggerWrapper;
import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static frc.robot.Constants.Toggles.useAdvantageKit;

public class Robot extends LoggedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    System.out.println("[Init] Robot");

    if (useAdvantageKit) {
      System.out.println("[Init] Starting AdvantageKit");
      LoggerWrapper.recordMetadata("Robot", Constants.robotName);
      LoggerWrapper.recordMetadata("RuntimeType", getRuntimeType().toString());
      LoggerWrapper.recordMetadata("ProjectName", BuildConstants.MAVEN_NAME);
      LoggerWrapper.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);
      LoggerWrapper.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
      LoggerWrapper.recordMetadata("GitDate", BuildConstants.GIT_DATE);
      LoggerWrapper.recordMetadata("GitBranch", BuildConstants.GIT_BRANCH);
      switch (BuildConstants.DIRTY) {
        case 0 -> LoggerWrapper.recordMetadata("GitDirty", "All changes committed");
        case 1 -> LoggerWrapper.recordMetadata("GitDirty", "Uncomitted changes");
        default -> LoggerWrapper.recordMetadata("GitDirty", "Unknown");
      }

      if (isReal()) {

        if (LogfileChecker(Constants.logFolder0)) {
          LoggerWrapper.addDataReceiver(new WPILOGWriter(Constants.logFolder0)); // Log to a USB stick ("/U/logs")
        } else if (LogfileChecker((Constants.logFolder1))) {
          LoggerWrapper.addDataReceiver(new WPILOGWriter(Constants.logFolder1)); // Log to a USB stick ("/U/logs")
        } else if (LogfileChecker((Constants.logFolder2))) {
          LoggerWrapper.addDataReceiver(new WPILOGWriter(Constants.logFolder2)); // Log to a USB stick ("/U/logs")
        } else if (LogfileChecker((Constants.logFolder3))) {
          LoggerWrapper.addDataReceiver(new WPILOGWriter(Constants.logFolder3)); // Log to a USB stick ("/U/logs")
        } else if (LogfileChecker((Constants.logFolder4))) {
          LoggerWrapper.addDataReceiver(new WPILOGWriter(Constants.logFolder4)); // Log to a USB stick ("/U/logs")
        } else if (LogfileChecker((Constants.logFolder5))) {
          LoggerWrapper.addDataReceiver(new WPILOGWriter(Constants.logFolder5)); // Log to a USB stick ("/U/logs")
        } else if (LogfileChecker((Constants.logFolder6))) {
          LoggerWrapper.addDataReceiver(new WPILOGWriter(Constants.logFolder6)); // Log to a USB stick ("/U/logs")
        } else if (LogfileChecker((Constants.logFolder7))) {
          LoggerWrapper.addDataReceiver(new WPILOGWriter(Constants.logFolder7)); // Log to a USB stick ("/U/logs")
        } else if (LogfileChecker((Constants.logFolder8))) {
          LoggerWrapper.addDataReceiver(new WPILOGWriter(Constants.logFolder8)); // Log to a USB stick ("/U/logs")
        } else if (LogfileChecker((Constants.logFolder9))) {
          LoggerWrapper.addDataReceiver(new WPILOGWriter(Constants.logFolder9)); // Log to a USB stick ("/U/logs")
        } else {
          System.out.println("No Log file Chosen!");
        }

        LoggerWrapper.addDataReceiver(new NT4Publisher()); // Publish data to NetworkTables
        LoggerWrapper.enablePowerDistributionLogging();
      } else if (!isSimulation()) {
        setUseTiming(false); // Run as fast as possible
        String logPath = LogFileUtil.findReplayLog(); // Pull the replay log from AdvantageScope (or prompt the user)
        LoggerWrapper.setReplaySource(new WPILOGReader(logPath)); // Read replay log
        LoggerWrapper.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim"))); // Save outputs to a new log
      }
      logActiveCommand();
      LoggerWrapper.start(); // Start logging! No more data receivers, replay sources, or metadata values may be added.


      try {
        m_robotContainer = new RobotContainer();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      System.out.println("[DONE] Robot");
    }
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void disabledExit() {
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void autonomousExit() {
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void teleopExit() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void testExit() {
  }


  private void logActiveCommand() {
    // Log active commands
    Map<String, Integer> commandCounts = new HashMap<>();
    BiConsumer<Command, Boolean> logCommandFunction =
            (Command command, Boolean active) -> {
              String name = command.getName();
              int count = commandCounts.getOrDefault(name, 0) + (active ? 1 : -1);
              commandCounts.put(name, count);
              LoggerWrapper.recordOutput(
                      "CommandsUnique/" + name + "_" + Integer.toHexString(command.hashCode()), active);
              LoggerWrapper.recordOutput("CommandsAll/" + name, count > 0);
            };
    CommandScheduler.getInstance()
            .onCommandInitialize(
                    (Command command) -> {
                      logCommandFunction.accept(command, true);
                    });
    CommandScheduler.getInstance()
            .onCommandFinish(
                    (Command command) -> {
                      logCommandFunction.accept(command, false);
                    });
    CommandScheduler.getInstance()
            .onCommandInterrupt(
                    (Command command) -> {
                      logCommandFunction.accept(command, false);
                    });
  }


  private boolean LogfileChecker(String file) {
    File testFile = new File(file+"/hello.txt");
    System.out.print("Checking log location " + testFile.getAbsolutePath());
    boolean check;
    try {
      testFile.createNewFile();
      check = true;
      testFile.delete();
    } catch (Exception e) {
      System.out.print(", exception = " + e.getMessage());
      check = false;
    }
    System.out.println(", check = " + check);

    return check;
  }
}
