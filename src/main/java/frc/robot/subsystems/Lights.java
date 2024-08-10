package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.lights.LightType;
import frc.utils.lights.LEDLightStrip;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.Constants;

import java.util.ArrayList;
import java.util.List;


public class Lights extends SubsystemBase {

    private static class Segment {
        private static int nextNumber = 0;
        final LightType lightType;
        final int numberOfLEDs;
        final int number;

        Segment(int numberOfLEDs, LightType lightType) {
            this.lightType = lightType;
            this.numberOfLEDs = numberOfLEDs;
            this.number = nextNumber++;
        }
    }

    // private final RobotState m_robotState;
    // private Shooter.ShooterState m_shooterState;
    //private StateAlliance m_alliance;
//        private int m_iteration;
//        private Pose2d cameraTestPose = new Pose2d(15.0423, 5.467, Rotation2d.fromDegrees(0));

    public final Color8Bit RED_COLOR;
    public final Color8Bit GREEN_COLOR;
    public final Color8Bit BLUE_COLOR;
    public final Color8Bit ORANGE_COLOR;
    public final Color8Bit YELLOW_COLOR;
    public final Color8Bit WHITE_COLOR;
    public final Color8Bit NO_COLOR = new Color8Bit(0, 0, 0);

    private Segment LEFT_SIDE;
    private Segment RIGHT_SIDE;

    private LEDLightStrip m_ledLightStrip;
    boolean m_ledColorRequested;

    int count = 0;

    public Lights() {
        RED_COLOR = scaleColor(new Color8Bit(255, 0, 0), Constants.Lights.brightness);
        GREEN_COLOR = scaleColor(new Color8Bit(0, 255, 0), Constants.Lights.brightness);
        BLUE_COLOR = scaleColor(new Color8Bit(0, 0, 255), Constants.Lights.brightness);
        ORANGE_COLOR = scaleColor(new Color8Bit(255, 32, 0), Constants.Lights.brightness);
        YELLOW_COLOR = scaleColor(new Color8Bit(255, 255, 0), Constants.Lights.brightness);
        WHITE_COLOR = scaleColor(new Color8Bit(84, 84, 84), Constants.Lights.brightness);

        initializeLights();
        System.out.println("Status Lights initializing ");
    }

    public void periodic() {
        // Lights may be expensive to check, and some updates can come too fast.
        // Keep a counter to make updates less frequent
       // ++m_iteration;

        setRingColor(LEFT_SIDE, ORANGE_COLOR);
        setRingColor(RIGHT_SIDE, GREEN_COLOR);

        if (m_ledColorRequested) {
            m_ledLightStrip.setLEDData();
            m_ledColorRequested = false;
        }
    }

    public void initializeLights() {
        System.out.println("init lights function");
        List<Segment> segments = new ArrayList<>();

        // These need to be added in the correct order. First string is closest to the roborio
        // Keep the assignment and segments.add together. This is necessary for the ring to have the right position

        LEFT_SIDE = new Segment(Constants.Lights.sideLength, LightType.getType(Constants.Lights.ringLEDType));
        segments.add(LEFT_SIDE);

        RIGHT_SIDE = new Segment(Constants.Lights.sideLength, LightType.getType(Constants.Lights.ringLEDType));
        segments.add(RIGHT_SIDE);

        m_ledLightStrip = new LEDLightStrip();
        for (Segment s : segments) {
            m_ledLightStrip.addSegment(s.numberOfLEDs, s.lightType);
        }
        m_ledLightStrip.setUp(Constants.Lights.port);
        m_ledColorRequested = true;
    }

    private static Color8Bit scaleColor(Color8Bit c, double s) {
        return new Color8Bit(MathUtil.clamp((int) (s * c.red), 0, 255),
                MathUtil.clamp((int) (s * c.green), 0, 255),
                MathUtil.clamp((int) (s * c.blue), 0, 255));
    }


    private void setRingColor(Segment s, Color8Bit c) {
        m_ledLightStrip.setLEDColor(s.number, c);
    }

    //private void setAlternatingRingColor(Segment s, Color8Bit c1, Color8Bit c2) {
       // m_ledLightStrip.setAlternatingLEDColor(s.number, c1, c2);
   // }

}