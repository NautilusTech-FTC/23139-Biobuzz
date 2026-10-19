package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.*;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.*;

@TeleOp
public class MainTeleOp extends OpMode {
    private DriveTrain drive;
    private Intake intake;
    private Intake transfer;
    private Shooter shooter;

    double lSY;
    double lSX;
    double rSX;

    double rT;
    boolean lB1;
    boolean rB1;
    boolean lB2;
    boolean rB2;

    boolean x;
    boolean y;
    boolean b;

    public void init() {
        drive = new DriveTrain(
                hardwareMap,
                "Motor0", "Motor1", "Motor2", "Motor3",
                REVERSE, FORWARD, FORWARD, REVERSE);

        intake = new Intake(hardwareMap, "IntakeMotor", FORWARD);
        transfer = new Intake(hardwareMap, "TransferMotor", FORWARD);
        shooter = new Shooter(hardwareMap, "ShooterMotor", "ShooterFlap", RUN_USING_ENCODER, FORWARD);
    }

    public void loop() {
        ctrlVars();

        intake.intake(lB1, rB1);
        transfer.intake(lB2, rB2);
        drive.operate(lSY, lSX, rSX, rT);
        shooter.shoot(x, y, b);
    }

    private void ctrlVars() {
        lSY = gamepad1.left_stick_y;
        lSX = gamepad1.left_stick_x;
        rSX = gamepad1.right_stick_x;
        rT = gamepad1.right_trigger;
        lB1 = gamepad1.left_bumper;
        rB1 = gamepad1.right_bumper;
        lB2 = gamepad2.left_bumper;
        rB2 = gamepad2.right_bumper;
        x = gamepad2.x;
        y = gamepad2.y;
        b = gamepad2.b;
    }
}
