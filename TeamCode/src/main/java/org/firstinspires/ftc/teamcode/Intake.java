package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private final DcMotor intake;
    private final CRServo transfer;

    private double power = 1.0;
    private double transferSpeed = 0.75;

    public Intake(HardwareMap hardwareMap, String intake, String transfer) {
        this.intake = hardwareMap.get(DcMotor.class, intake);
        this.transfer = hardwareMap.get(CRServo.class, transfer);
    }

    public void setIntakePower(double maxPower) {
        this.power = maxPower;
    }

    public void setTransferSpeed(double transferSpeed) {
        this.transferSpeed = transferSpeed;
    }

    public void intakeTransfer(boolean in, boolean out) {

        if (in && !out) {
            intake.setPower(-power);
            transfer.setPower(-transferSpeed);
        } else if (out && !in) {
            intake.setPower(power);
            transfer.setPower(transferSpeed);
        } else {
            intake.setPower(0);
            transfer.setPower(0);
        }
    }
}
