package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.lights.LightType;
import frc.utils.lights.LEDLightStrip;
import frc.utils.lights.LightType;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.ArrayList;
import java.util.List;


public class Lights extends SubsystemBase{
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

    private Segment left;
    private Segment right;
    private int m_iteration;
    boolean m_ledColorRequested;
    private LEDLightStrip m_ledLightStrip;
    public final Color8Bit RED_COLOR;
    public final Color8Bit TEAL_COLOR;
    public final Color8Bit BLUE_COLOR;
    public final Color8Bit PINK_COLOR;
    public final Color8Bit PURPLE_COLOR;
    public final Color8Bit WHITE_COLOR;
    public final Color8Bit NO_COLOR = new Color8Bit(0, 0, 0);

    public Lights(){

        RED_COLOR = new Color8Bit(255, 0, 0);
        TEAL_COLOR = new Color8Bit(0,255,191);
        BLUE_COLOR = new Color8Bit(0, 0, 255);
        PINK_COLOR = new Color8Bit(255,13,154);
        PURPLE_COLOR = new Color8Bit(203,13,255);
        WHITE_COLOR = new Color8Bit(255,255,255);

    }
    public void periodic(){
        if (m_ledColorRequested) {
            m_ledLightStrip.setLEDData();
        }


    }

    public void initializeLights() {
        List<Segment> segments = new ArrayList<>();

        // These need to be added in the correct order. First string is closest to the roborio
        // Keep the assignment and segments.add together. This is necessary for the ring to have the right position

        //left = new Segment(Constants.Lights.sideTopLength, LightType.getType(Constants.Lights.ringLEDType));
        segments.add(left);

        m_ledLightStrip = new LEDLightStrip();
        for (Segment s : segments) {
            m_ledLightStrip.addSegment(s.numberOfLEDs, s.lightType);
        }
        m_ledLightStrip.setUp(Constants.Lights.port);
        m_ledColorRequested = true;
    }

    
}
