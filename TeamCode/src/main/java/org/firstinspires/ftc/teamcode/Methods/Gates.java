package org.firstinspires.ftc.teamcode.Methods;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Gates {
    private final Servo gate;

    private double closeGate;
    private double openGate;

    public Gates(HardwareMap hardwareMap, String gate) {
        this.gate = hardwareMap.get(Servo.class, gate);
    }

    public void setCloseGate(double closePos) {
        closeGate = closePos;
    }

    public void setOpenGate(double openPos) {
        openGate = openPos;
    }

    public void ctrlGate(boolean open, boolean close) {
        if (open) {
            gate.setPosition(openGate);
        }

        if (close) {
            gate.setPosition(closeGate);
        }
    }
}
