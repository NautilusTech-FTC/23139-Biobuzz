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

import java.util.ArrayList;
import java.util.List;

public class AprilTag {
    private Position cameraPosition = new Position(DistanceUnit.INCH,
            0, 0, 0, 0);
    private YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
            0, 0, 0, 0);

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

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
    }

    public void checkHiveTip () {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        List<Integer> tagIds = new ArrayList<>();
        for (AprilTagDetection detection : currentDetections) {

        }

    }
}
