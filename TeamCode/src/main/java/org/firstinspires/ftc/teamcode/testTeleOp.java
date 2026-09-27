package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class testTeleOp extends OpMode {
    private DcMotor motor;
    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "test");

    }

    @Override
    public void loop() {

    }
}
