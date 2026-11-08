package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp
public class MotorStats extends OpMode {
    private DcMotorEx motor;
    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, "TestMotor");
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        if (gamepad1.b) {
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        motor.setPower(1);

        telemetry.addData("Shooter Amps", motor.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("Shooter Velocity", motor.getVelocity());
        telemetry.addData("Direction (A/B to change)", motor.getDirection());
    }
}
