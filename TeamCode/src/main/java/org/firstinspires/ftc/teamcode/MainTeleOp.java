package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.*;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.*;

@Configurable
@TeleOp
public class MainTeleOp extends OpMode {
    private DriveTrain drive;
    private Intake intake;
    private Intake transfer;
    private Shooter shooterP;
    private Shooter shooterN;
    private Flower flower;

    double lSY;
    double lSX;
    double rSX;

    double rT;
    boolean lB1;
    boolean rB1;
    boolean lB2;
    boolean rB2;

    boolean x;
    boolean b;
    boolean r;
    boolean l;
    boolean y;
    boolean a;

    boolean down;
    boolean up;

    public static double pollenV = 920;
    public static double nectarV = 940;

    public void init() {
        drive = new DriveTrain(
                hardwareMap,
                "FrontLeft", "FrontRight", "BackRight", "BackLeft",
                REVERSE, FORWARD, FORWARD, REVERSE);

        intake = new Intake(hardwareMap, "IntakeMotor", FORWARD);
        transfer = new Intake(hardwareMap, "TransferMotor", FORWARD);
        shooterP = new Shooter(hardwareMap, "PollenMotor", "PollenGate", RUN_USING_ENCODER, REVERSE);
        shooterN = new Shooter(hardwareMap, "NectarMotor", "NectarGate", RUN_USING_ENCODER, REVERSE);
        flower = new Flower(hardwareMap, "FlowerRamp", Servo.Direction.REVERSE);

        shooterP.setCloseGate(0.269);
        shooterP.setOpenGate(0.303);
        shooterN.setCloseGate(0);
        shooterN.setOpenGate(0.02);
        flower.setIntakeDown(0.6);
        flower.setIntakeUp(0);
    }

    public void loop() {
        ctrlVars();

        intake.intake(lB1, rB1);
        transfer.intake(lB2, rB2);
        drive.operate(lSY, lSX, rSX, rT);
        shooterP.shoot(x, y, b, pollenV);
        shooterN.shoot(l, a, r, nectarV);
        flower.lift(down, up);

        telemetry.addData("Pollen V: ", shooterP.getShooterVelocity());
        telemetry.addData("Nectar V: ", shooterN.getShooterVelocity());
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
        b = gamepad2.b;
        r = gamepad2.dpad_right;
        l = gamepad2.dpad_left;
        y = gamepad2.y;
        a = gamepad2.a;
        down = gamepad1.dpad_down;
        up = gamepad1.dpad_up;
    }
}
