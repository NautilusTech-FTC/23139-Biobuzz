package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.controllers.Controller;
import com.pedropathing.controllers.PIDController;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.*;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.*;

import org.firstinspires.ftc.teamcode.Methods.DriveTrain;
import org.firstinspires.ftc.teamcode.Methods.Flower;
import org.firstinspires.ftc.teamcode.Methods.Intake;
import org.firstinspires.ftc.teamcode.Methods.Shooter;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Configurable
@TeleOp
public class MainTeleOp extends OpMode {
    private DriveTrain drive;
    private Intake intake;
    private Intake transfer;
    private Shooter shooterP;
    private Shooter shooterN;
    private Flower flower;

    private Follower follower;

    double lSY,lSX,rSX;

    double rT;
    boolean lB1,rB1;
    boolean lB2,rB2;

    boolean x, b, r, l, y, u;
    boolean aimButton;

    boolean down;
    boolean up;

    public static double pollenV = 920;
    public static double nectarV = 940;

    private Robot.drives driveMode = Robot.drives.PEDRO_ROBOT_CENTRIC;

    public void init() {
        //instance subsystems:
        drive = new DriveTrain(
                hardwareMap,
                "FrontLeft", "FrontRight", "BackRight", "BackLeft",
                REVERSE, FORWARD, FORWARD, REVERSE);

        intake = new Intake(hardwareMap, "IntakeMotor", FORWARD);
        transfer = new Intake(hardwareMap, "TransferMotor", FORWARD);
        shooterP = new Shooter(hardwareMap, "PollenMotor", "PollenGate", RUN_USING_ENCODER, REVERSE);
        shooterN = new Shooter(hardwareMap, "NectarMotor", "NectarGate", RUN_USING_ENCODER, REVERSE);
        flower = new Flower(hardwareMap, "FlowerRamp", Servo.Direction.REVERSE);

        //config shooters
        shooterP.setCloseGate(0.269);
        shooterP.setOpenGate(0.303);
        shooterN.setCloseGate(0);
        shooterN.setOpenGate(0.02);
        flower.setIntakeDown(0.6);
        flower.setIntakeUp(0);

        //Fetch from robot data
        driveMode = Robot.Infoz.drive;

        //init pedro
        follower = Constants.create(hardwareMap);
        follower.setPose(Robot.Infoz.autoEndPose);


    }

    public void loop() {
        ctrlVars();

        drive();

        intake.intake(lB1, rB1);
        transfer.intake(lB2, rB2);
        shooterP.shoot(x, y, b, pollenV);
        shooterN.shoot(l, u, r, nectarV);
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
        u = gamepad2.dpad_up;
        down = gamepad1.dpad_down;
        up = gamepad1.dpad_up;

        aimButton = gamepad1.a;
    }

    private void drive() {
        DrivePowers powers = null;
        switch (driveMode) { //Switch based off of drivemode
            case ROBOT_CENTRIC:
                drive.operate(lSY, lSX, rSX, rT);
                return; //This is so we can have pedro code below the switch statement that will not run if its set to normal robot centric

            case PEDRO_ROBOT_CENTRIC:
                powers = new DrivePowers(-lSY, -lSX, -rSX);
                break;

            case PEDRO_FIELD_CENTRIC:
                powers = ManualDrive.fieldCentric(
                        -lSY, lSX, rSX,
                        follower.pose().heading());
                follower.manual(powers);
                break;
        }

        Pose pose = follower.pose();
        //TODO: make this changed based off of team and hive position
        double targetX = 57.9;
        double targetY = 51;

        if(aimButton) {
            //AIM CODE
            double angle = Math.atan2(targetX-pose.x(), targetY-pose.y());
            ManualDrive.headingLock(follower, Controller.proportional(0.25), powers, angle);
        } else {
            //NORMAL DRIVE
            ManualDrive.driveOrHold(follower, powers);
        }

        follower.update();
    }
}
