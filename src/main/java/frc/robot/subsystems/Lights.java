package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.lights.LightType;
import frc.utils.lights.LEDLightStrip;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * The Lights subsystem controls the LED light strips on the robot.
 */
public class Lights extends SubsystemBase {

    /**
     * Represents a segment of the LED light strip.
     */
    private static class Segment {
        private static int nextNumber = 0;
        final LightType lightType;
        final int numberOfLEDs;
        final int number;

        /**
         * Constructs a new Segment.
         *
         * @param numberOfLEDs the number of LEDs in the segment
         * @param lightType the type of light in the segment
         */
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

    /**
     * Constructs a new Lights subsystem and initializes the colors and lights.
     */
    public Lights() {
        RED_COLOR = scaleColor(new Color8Bit(255, 0, 0), Constants.Lights.brightness);
        GREEN_COLOR = scaleColor(new Color8Bit(0, 255, 0), Constants.Lights.brightness);
        BLUE_COLOR = scaleColor(new Color8Bit(0, 0, 255), Constants.Lights.brightness);
        ORANGE_COLOR = scaleColor(new Color8Bit(255, 32, 0), Constants.Lights.brightness);
        YELLOW_COLOR = scaleColor(new Color8Bit(255, 255, 0), Constants.Lights.brightness);
        WHITE_COLOR = scaleColor(new Color8Bit(86, 86, 86), Constants.Lights.brightness);
        PURPLE_COLOR = scaleColor(new Color8Bit(138,0,196), Constants.Lights.brightness);
        PINK_COLOR = scaleColor(new Color8Bit(255,5,90), Constants.Lights.brightness);
        TEAL_COLOR = scaleColor(new Color8Bit(0,128,128), Constants.Lights.brightness);
        initializeLights();
        System.out.println("Status Lights initializing ");
    }

    /**
     * This method is called periodically by the scheduler.
     */
    public void periodic() {
        // Lights may be expensive to check, and some updates can come too fast.
        // Keep a counter to make updates less frequent
        runway(Constants.Lights.direction, PINK_COLOR, TEAL_COLOR, Constants.Lights.runwayLength);
        m_ledLightStrip.setLEDData();
    }

    /**
     * Initializes the lights by setting up the segments and the LED light strip.
     */
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

    /**
     * Scales the color by the given scale factor.
     *
     * @param c the original color
     * @param s the scale factor
     * @return the scaled color
     */
    private static Color8Bit scaleColor(Color8Bit c, double s) {
        return new Color8Bit(MathUtil.clamp((int) (s * c.red), 0, 255),
                MathUtil.clamp((int) (s * c.green), 0, 255),
                MathUtil.clamp((int) (s * c.blue), 0, 255));
    }

    /**
     * Sets the color of the given segment.
     *
     * @param s the segment
     * @param c the color
     */
    private void setSegmentColor(Segment s, Color8Bit c) {
        m_ledLightStrip.setLEDColor(s.number, c);
    }

    /**
     * Runs the runway light effect.
     *
     * @param forward the direction of the runway effect
     * @param backColor the background color
     * @param stripColor the color of the runway strip
     * @param runwayLength the length of the runway
     */
    private void runway(boolean forward, Color8Bit backColor, Color8Bit stripColor, int runwayLength) {
        setSegmentColor(LEFT_SIDE, backColor);
        setSegmentColor(RIGHT_SIDE, backColor);
        runway_setIndex(forward, LEFT_SIDE, stripColor, runwayLength);
        runway_setIndex(forward, RIGHT_SIDE, stripColor, runwayLength);

        if (forward)
            runwayIndex += 1;
        else
            runwayIndex -= 1;
    }

    /**
     * Sets the color of the runway strip at the current index.
     *
     * @param forward the direction of the runway effect
     * @param side the segment of the light strip
     * @param stripColor the color of the runway strip
     * @param runwayLength the length of the runway
     */
    private void runway_setIndex(boolean forward, Segment side, Color8Bit stripColor, int runwayLength) {
        for (int index = 0; index < runwayLength; index++) {
            m_ledLightStrip.setLEDColor(side.number, runwayIndex + index, stripColor);
        }
    }
}