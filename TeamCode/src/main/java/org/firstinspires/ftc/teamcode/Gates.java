package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Gates {
    private Servo pollenGate;
    private Servo nectarGate;


    public void init(HardwareMap hardwareMap) {
        pollenGate = hardwareMap.get(Servo.class, "PollenGate");
        nectarGate = hardwareMap.get(Servo.class, "NectarGate");
    }

    public void closePollenGate() {
        pollenGate.setPosition(0.269);
    }

    public void openPollenGate() {
        pollenGate.setPosition(0.303);
    }

    public void closeNectarGate() {
        nectarGate.setPosition(0);
    }

    public void openNectarGate() {
        nectarGate.setPosition(0.02);
    }
}
