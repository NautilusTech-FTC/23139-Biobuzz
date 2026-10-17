package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.*;

@TeleOp
public class MainTeleOp extends OpMode {
    private DriveTrain drive;
    private Intake intake;

    double lSY;
    double lSX;
    double rSX;

    double rT;
    boolean lB;
    boolean rB;

    public void init() {
        drive = new DriveTrain(
                hardwareMap,
                "Motor0", "Motor1", "Motor2", "Motor3",
                REVERSE, FORWARD, FORWARD, REVERSE);

        intake = new Intake(hardwareMap, "IntakeMotor", "TransferServo");
    }

    public void loop() {
        ctrlVars();

        intake.intakeTransfer(lB, rB);
        drive.operate(lSY, lSX, rSX, rT);
    }

    private void ctrlVars() {
        lSY = gamepad1.left_stick_y;
        lSX = gamepad1.left_stick_x;
        rSX = gamepad1.right_stick_x;
        rT = gamepad1.right_trigger;
        lB = gamepad2.left_bumper;
        rB = gamepad2.right_bumper;
    }
}
