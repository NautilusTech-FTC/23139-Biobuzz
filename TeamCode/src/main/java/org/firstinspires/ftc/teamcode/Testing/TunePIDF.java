package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.*;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.*;

import org.firstinspires.ftc.teamcode.Methods.DriveTrain;
import org.firstinspires.ftc.teamcode.Methods.Intake;
import org.firstinspires.ftc.teamcode.Methods.PIDFShooter;

@TeleOp
public class TunePIDF extends OpMode {
    private DriveTrain drive;
    private Intake intake;
    private Intake transfer;
    private PIDFShooter shooterP;
    private PIDFShooter shooterN;

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
    boolean a;
    boolean b;

    boolean down;
    boolean up;
    boolean left;
    boolean right;

    public void init() {
        drive = new DriveTrain(
                hardwareMap,
                "Motor0", "Motor1", "Motor2", "Motor3",
                REVERSE, FORWARD, FORWARD, REVERSE);

        intake = new Intake(hardwareMap, "IntakeMotor", FORWARD);
        transfer = new Intake(hardwareMap, "TransferMotor", FORWARD);
        shooterP = new PIDFShooter(hardwareMap, "PollenMotor", "PollenGate", RUN_USING_ENCODER, REVERSE);
        shooterN = new PIDFShooter(hardwareMap, "NectarMotor", "NectarGate", RUN_USING_ENCODER, REVERSE);

        shooterP.setCloseGate(0.269);
        shooterP.setOpenGate(0.303);
        shooterN.setCloseGate(0);
        shooterN.setOpenGate(0.02);
    }

    public void loop() {
        ctrlVars();

        intake.intake(lB1, rB1);
        transfer.intake(lB2, rB2);
        drive.operate(lSY, lSX, rSX, rT);
        shooterP.shoot(x, y, b);
        //shooterN.shoot(x, a, b);

        shooterP.pidf.tunePIDF(a, down, up, left, right);
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
        a = gamepad2.a;
        b = gamepad2.b;

        down = gamepad2.dpad_down;
        up = gamepad2.dpad_up;
        left = gamepad2.dpad_left;
        right = gamepad2.dpad_right;
    }
}
