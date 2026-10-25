package org.firstinspires.ftc.teamcode.Methods;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private final DcMotor intake;

    private double power = 1.0;

    public Intake(HardwareMap hardwareMap, String intake, DcMotor.Direction direction) {
        this.intake = hardwareMap.get(DcMotor.class, intake);
        this.intake.setDirection(direction);
    }

    public void setIntakePower(double maxPower) {
        this.power = maxPower;
    }

    public void intake(boolean in, boolean out) {

        if (in && !out) {
            intake.setPower(-power);
        } else if (out && !in) {
            intake.setPower(power);
        } else {
            intake.setPower(0);
        }
    }
}
