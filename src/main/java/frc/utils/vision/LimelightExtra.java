package frc.utils.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightExtra {


    public boolean hasValidTarget(String limelight) { //Checks if the limelight has a valid target
        NetworkTable table = NetworkTableInstance.getDefault().getTable(limelight);
        return table.getEntry("tv").getDouble(0) != 0;
    }


    public void turnLightOff(String limelight) { //Turns off the limelight LED
        NetworkTable table = NetworkTableInstance.getDefault().getTable(limelight);
        table.getEntry("ledMode").setNumber(1);
    }

    public double getLatency(String limelight) { //Gets the latency of the limelight
        NetworkTable table = NetworkTableInstance.getDefault().getTable(limelight);
        return table.getEntry("tl").getDouble(0);
    }

    public double getPipeline(String limelight) { //Gets the current pipeline of the limelight
        NetworkTable table = NetworkTableInstance.getDefault().getTable(limelight);
        return table.getEntry("getpipe").getDouble(0);
    }
    public String getJson(String limelight) { //Gets the JSON data from the limelight
        NetworkTable table = NetworkTableInstance.getDefault().getTable(limelight);
        return table.getEntry("json").getString("0");
    }
    public double getTargetArea(String limelight) { //Gets the area of the target
        NetworkTable table = NetworkTableInstance.getDefault().getTable(limelight);
        return table.getEntry("ta").getDouble(0);
    }
    public double getTxnc(String limelight) { //Gets the Horizontal Offset From Principal Pixel To Target
        NetworkTable table = NetworkTableInstance.getDefault().getTable(limelight);
        return table.getEntry("txnc").getDouble(0);
    }
    public double getTync(String limelight) { //Gets the Vertical Offset From Principal Pixel To Target
        NetworkTable table = NetworkTableInstance.getDefault().getTable(limelight);
        return table.getEntry("tync").getDouble(0);
    }
    public void setCrop(int x, int y, int w, int h, String limelight) { //Sets the crop of the limelight
        NetworkTable table = NetworkTableInstance.getDefault().getTable(limelight);
        table.getEntry("camMode").setNumber(1);
        table.getEntry("stream").setNumber(2);
        table.getEntry("x").setNumber(x);
        table.getEntry("y").setNumber(y);
        table.getEntry("w").setNumber(w);
        table.getEntry("h").setNumber(h);
    }
    public void changePipeline(int pipeline, String limelight) { //Changes the pipeline of the limelight
        NetworkTable table = NetworkTableInstance.getDefault().getTable(limelight);
        table.getEntry("pipeline").setNumber(pipeline);
    }


}
