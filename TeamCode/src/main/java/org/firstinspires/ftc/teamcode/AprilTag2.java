package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class AprilTag2 {

    public void init(HardwareMap hardwareMap) {
        WebcamName webcam = hardwareMap.get(WebcamName.class, "WebcamLogitech");

    }
}
