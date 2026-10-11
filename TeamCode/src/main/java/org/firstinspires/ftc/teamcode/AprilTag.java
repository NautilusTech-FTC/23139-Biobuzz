package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Mat;

import java.util.List;

public class AprilTag {
    private Position cameraPosition = new Position(DistanceUnit.INCH,
            0, 0, 0, 0);
    private YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
            0, -90, 0, 0);

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    public enum hiveTip {
        AUDIENCE_SIDE_UP,
        SCORING_SIDE_UP
    }

    public hiveTip currentTip;

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

    //Returns true if the robot is on the correct side to shoot from (ie can see the april tags)
     public boolean checkHiveShootability () {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection instanceof AprilTagClusterDetection) {
                AprilTagClusterDetection tag = (AprilTagClusterDetection) detection;

                if ((Robot.Infoz.alliance == Robot.alliances.BLUE_ALLIANCE) && (tag.metadata.name.charAt(0) == 'B')) {
                    return true;
                }

                if ((Robot.Infoz.alliance == Robot.alliances.RED_ALLIANCE) && (tag.metadata.name.charAt(0) == 'R')) {
                    return true;
                }
            }
        }

        return false;
    }

    public void setHiveTip () {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection instanceof AprilTagClusterDetection) {
                AprilTagClusterDetection tag = (AprilTagClusterDetection) detection;

                if ((Robot.Infoz.alliance == Robot.alliances.BLUE_ALLIANCE) && (tag.metadata.name.equals("BLUE AUDIENCE")) && (Math.abs(tag.ftcPose.roll) < 90)) {
                    currentTip = hiveTip.AUDIENCE_SIDE_UP;
                } else if ((Robot.Infoz.alliance == Robot.alliances.BLUE_ALLIANCE) && tag.metadata.name.equals("BLUE AUDIENCE")) {
                    currentTip = hiveTip.SCORING_SIDE_UP;
                } else if ((Robot.Infoz.alliance == Robot.alliances.BLUE_ALLIANCE) && (Math.abs(tag.ftcPose.roll) < 90)) {
                    currentTip = hiveTip.SCORING_SIDE_UP;
                } else if (Robot.Infoz.alliance == Robot.alliances.BLUE_ALLIANCE) {
                    currentTip = hiveTip.AUDIENCE_SIDE_UP;
                }

                if ((Robot.Infoz.alliance == Robot.alliances.RED_ALLIANCE) && (tag.metadata.name.equals("RED AUDIENCE")) && (Math.abs(tag.ftcPose.roll) < 90)) {
                    currentTip = hiveTip.AUDIENCE_SIDE_UP;
                } else if ((Robot.Infoz.alliance == Robot.alliances.RED_ALLIANCE) && tag.metadata.name.equals("RED AUDIENCE")) {
                    currentTip = hiveTip.SCORING_SIDE_UP;
                } else if ((Robot.Infoz.alliance == Robot.alliances.RED_ALLIANCE) && (Math.abs(tag.ftcPose.roll) < 90)) {
                    currentTip = hiveTip.SCORING_SIDE_UP;
                } else if (Robot.Infoz.alliance == Robot.alliances.RED_ALLIANCE) {
                    currentTip = hiveTip.AUDIENCE_SIDE_UP;
                }
            }
        }
    }
}
