package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class TuneServo extends OpMode {
    private Servo servo;
    public double position;

    public void init () {
        servo = hardwareMap.get(Servo.class, "testservo");
    }

    public void start() {
        position = 0;
        servo.setPosition(0);
    }

    public void loop() {
        if (gamepad1.dpad_left) {
            position -= 0.001;
        } else if (gamepad1.dpad_right){
            position += 0.001;
        }
        servo.setPosition(position);

        telemetry.addData("position", position);
        telemetry.update();
    }
}

