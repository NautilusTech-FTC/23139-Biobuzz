package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;
import java.util.List;

public class AprilTag {
    private Position cameraPosition = new Position(DistanceUnit.INCH,
            0, 0, 0, 0);
    private YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
            0, 0, 0, 0);

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    List<Integer> tagIds = new ArrayList<>();
    List<Integer> redIds = new ArrayList<>(List.of(30, 31, 32, 33, 34, 35, 36, 37));
    List<Integer> blueIds = new ArrayList<>(List.of(38, 39, 40, 41, 42, 43, 44, 45));

    public void init (HardwareMap hardwareMap) {
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "WebcamLogitech");
        aprilTag = new AprilTagProcessor.Builder()
                .setCameraPose(cameraPosition, cameraOrientation)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();

        builder.setCamera(hardwareMap.get(WebcamName.class, "WebcamLogitech"));
        builder.setCameraResolution(new Size(640, 480));

        builder.addProcessor(aprilTag);

        visionPortal = builder.build();

        if (Robot.Infoz.alliance == Robot.alliances.BLUE_ALLIANCE) {
            tagIds = blueIds;
        } else {
            tagIds = redIds;
        }
    }

    //Returns true if the robot is on the correct side to shoot from (ie can see the april tags)
     public boolean checkHiveTip () {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection tag = (AprilTagSingleDetection) detection;

                if (tagIds.contains(tag.id)) {
                    return true;
                }
            }
        }

        return false;
    }
}
