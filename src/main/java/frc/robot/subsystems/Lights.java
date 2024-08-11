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


    public final Color8Bit RED_COLOR;
    public final Color8Bit GREEN_COLOR;
    public final Color8Bit BLUE_COLOR;
    public final Color8Bit ORANGE_COLOR;
    public final Color8Bit YELLOW_COLOR;
    public final Color8Bit WHITE_COLOR;
    public final Color8Bit TEAL_COLOR;
    public final Color8Bit PINK_COLOR;
    public final Color8Bit PURPLE_COLOR;
    private int runwayIndex = 0;

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
        PURPLE_COLOR = scaleColor(new Color8Bit(138,0,196), Constants.Lights.brightness);
        PINK_COLOR = scaleColor(new Color8Bit(255,5,90), Constants.Lights.brightness);
        TEAL_COLOR = scaleColor(new Color8Bit(0,128,128), Constants.Lights.brightness);
        initializeLights();
        System.out.println("Status Lights initializing ");
    }

    public void periodic() {
        // Lights may be expensive to check, and some updates can come too fast.
        // Keep a counter to make updates less frequent
        setSegmentColor(LEFT_SIDE, ORANGE_COLOR);
        setSegmentColor(RIGHT_SIDE, GREEN_COLOR);
        runway();
        m_ledLightStrip.setLEDData();
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


    private void setSegmentColor(Segment s, Color8Bit c) {
        m_ledLightStrip.setLEDColor(s.number, c);
    }



    private void runway (){
        m_ledLightStrip.setLEDColor(0,(runwayIndex) % LEFT_SIDE.numberOfLEDs, TEAL_COLOR);
        m_ledLightStrip.setLEDColor(0,(runwayIndex+1) % LEFT_SIDE.numberOfLEDs, TEAL_COLOR);
        m_ledLightStrip.setLEDColor(0,(runwayIndex+2) % LEFT_SIDE.numberOfLEDs, TEAL_COLOR);
        m_ledLightStrip.setLEDColor(0,(LEFT_SIDE.numberOfLEDs + runwayIndex-1) % LEFT_SIDE.numberOfLEDs, TEAL_COLOR);
        m_ledLightStrip.setLEDColor(0,(LEFT_SIDE.numberOfLEDs + runwayIndex-2) % LEFT_SIDE.numberOfLEDs, TEAL_COLOR);
        m_ledLightStrip.setLEDColor(0,(LEFT_SIDE.numberOfLEDs + runwayIndex-3) % LEFT_SIDE.numberOfLEDs, TEAL_COLOR);
        m_ledLightStrip.setLEDColor(1,(runwayIndex) % LEFT_SIDE.numberOfLEDs, TEAL_COLOR);
        m_ledLightStrip.setLEDColor(1,(runwayIndex+1) % LEFT_SIDE.numberOfLEDs, TEAL_COLOR);
        m_ledLightStrip.setLEDColor(1,(runwayIndex+2) % LEFT_SIDE.numberOfLEDs, TEAL_COLOR);
        m_ledLightStrip.setLEDColor(1,(LEFT_SIDE.numberOfLEDs + runwayIndex-1) % LEFT_SIDE.numberOfLEDs, TEAL_COLOR);
        m_ledLightStrip.setLEDColor(1,(LEFT_SIDE.numberOfLEDs + runwayIndex-2) % LEFT_SIDE.numberOfLEDs, TEAL_COLOR);
        m_ledLightStrip.setLEDColor(1,(LEFT_SIDE.numberOfLEDs + runwayIndex-3) % LEFT_SIDE.numberOfLEDs, TEAL_COLOR);
        runwayIndex += 1;
    }

}