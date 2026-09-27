package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.ServoFlavor;

public class Intake2 {
    private final DcMotor intake;
    private final CRServo transfer;

    private double minPower = 0.1;
    private double maxPower = 1.0;
    private double transferSpeed = 0.75;

    public Intake2(HardwareMap hardwareMap, String intake, String transfer) {
        this.intake = hardwareMap.get(DcMotor.class, intake);
        this.transfer = hardwareMap.get(CRServo.class, transfer);
    }

    public void setMinPower(double minPower) {
        this.minPower = minPower;
    }

    public void setMaxPower(double maxPower) {
        this.maxPower = maxPower;
    }

    public void setTransferSpeed(double transferSpeed) {
        this.transferSpeed = transferSpeed;
    }

    public void intakeTransfer(boolean in, boolean out, double brakes) {

        if (in && !out) {
            intake.setPower(Math.min(-maxPower + brakes, -minPower));
            transfer.setPower(-transferSpeed);
        } else if (out && !in) {
            intake.setPower(Math.max(maxPower - brakes, minPower));
            transfer.setPower(transferSpeed);
        } else {
            intake.setPower(0);
            transfer.setPower(0);
        }
    }
}
