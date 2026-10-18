package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Transfer {
    private final DcMotor transfer;

    private double power = 0.75;

    public Transfer(HardwareMap hardwareMap, String transfer) {
        this.transfer = hardwareMap.get(DcMotor.class, transfer);
    }

    public void setTransferPower(double power) {
        this.power = power;
    }

    public void transfer(boolean in, boolean out) {

        if (in && !out) {
            transfer.setPower(-power);
        } else if (out && !in) {
            transfer.setPower(power);
        } else {
            transfer.setPower(0);
        }
    }
}
