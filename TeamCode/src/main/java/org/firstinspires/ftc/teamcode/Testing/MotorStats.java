package org.firstinspires.ftc.teamcode.Testing;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import java.util.Timer;

@Disabled
@TeleOp
public class MotorStats extends OpMode {
    private DcMotorEx motor;
    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, "TestMotor");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    TelemetryManager panels = PanelsTelemetry.INSTANCE.getTelemetry();
    int lastpos = 0, pos;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void loop() {
        if (gamepad1.a) {
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        if (gamepad1.b) {
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        motor.setPower(1);



        /*telemetry.addData("Shooter Amps", motor.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("Shooter Velocity", motor.getVelocity());
        telemetry.addData("Direction (A/B to change)", motor.getDirection());*/
        pos = motor.getCurrentPosition();
        panels.addData("Custom Vel", (pos-lastpos)/timer.seconds());
        timer.reset();
        lastpos = pos;
        panels.addData("FTC Vel", motor.getVelocity());
        panels.addData("Shooter Amps", motor.getCurrent(CurrentUnit.AMPS));
        panels.update();

    }
}
