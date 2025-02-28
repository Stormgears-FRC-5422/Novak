package frc.robot.subsystems.drive;

public class DrivetrainFactory {
    protected static DrivetrainBase instance;

    public static DrivetrainBase getInstance(String driveType) throws IllegalDriveTypeException {
        if (instance ==  null) {
            System.out.println("Initializing " + driveType);
            switch (driveType.toLowerCase()) {
                case "drivetrain" -> instance = new Drivetrain();
                case "diagnosticdrive" -> instance = new DiagnosticDrive();
                case "ctrdrivetrain" -> instance = new CTRDrivetrain();
                default -> throw new IllegalDriveTypeException("Illegal Drive Type: " + driveType + " ---!");
            }
        }
        return instance;
    }
}
