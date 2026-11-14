package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.field.FieldManager;
import com.bylazar.field.PanelsField;
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

import static org.firstinspires.ftc.teamcode.AprilTag.hiveTip;

import org.firstinspires.ftc.teamcode.Methods.DriveTrain;
import org.firstinspires.ftc.teamcode.Methods.Flower;
import org.firstinspires.ftc.teamcode.Methods.Gates;
import org.firstinspires.ftc.teamcode.Methods.Intake;
import org.firstinspires.ftc.teamcode.Methods.PIDFShooter;
import org.firstinspires.ftc.teamcode.Methods.Shooter;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Configurable
@TeleOp
public class MainTeleOp extends OpMode {
    private final PanelsField instance = PanelsField.INSTANCE;
    private DriveTrain drive;
    private Intake intake;
    private Intake transfer;
    private PIDFShooter shooterP;
    private Shooter shooterN;
    private Flower flower;

    private AprilTag aprilTag = new AprilTag();

    private Follower follower;

    double lSY,lSX,rSX;

    double rT;
    boolean lB1,rB1;
    boolean lB2,rB2;

    boolean x, b, l, r, y, a, u, d;
    boolean aimButton;

    boolean down;
    boolean up;

    public static double pollenV = 920;
    public static double nectarV = 940;

    private Robot.drives driveMode = Robot.drives.PEDRO_ROBOT_CENTRIC;
    FieldManager field = PanelsField.INSTANCE.getField();

    public void init() {
        //instance subsystems:
        drive = new DriveTrain(
                hardwareMap,
                "FrontLeft", "FrontRight", "BackRight", "BackLeft",
                REVERSE, FORWARD, FORWARD, REVERSE);

        intake = new Intake(hardwareMap, "IntakeMotor", FORWARD);
        transfer = new Intake(hardwareMap, "TransferMotor", FORWARD);
        shooterP = new PIDFShooter(hardwareMap, "PollenMotor", "PollenGate", RUN_USING_ENCODER, REVERSE);
        shooterN = new Shooter(hardwareMap, "NectarMotor", "NectarGate", RUN_USING_ENCODER, REVERSE);
        flower = new Flower(hardwareMap, "FlowerRamp", Servo.Direction.REVERSE);

        //config shooters
        shooterP.setCloseGate(0.269);
        shooterP.setOpenGate(0.303);
        shooterP.pidf.setPIDF(0.0047, 0, 0.000019, 0.0003);
        shooterN.setCloseGate(0);
        shooterN.setOpenGate(0.02);
        flower.setIntakeDown(0.6);
        flower.setIntakeUp(0);

        //Fetch from robot data
        driveMode = Robot.Infoz.drive;

        //init pedro
        follower = Constants.create(hardwareMap);
        follower.setPose(Robot.Infoz.autoEndPose);

        field.setOffsets(PanelsField.INSTANCE.getPresets().getPEDRO_PATHING());
        field.setStyle("red", "blue", 2.0);

        aprilTag.init(hardwareMap);



    }

    public void loop() {
        ctrlVars();

        drive();

        intake.intake(lB1, rB1);
        transfer.intake(lB2, rB2);
        shooterP.shoot(x, b, pollenV);
        shooterN.shoot(l, r, nectarV);
        shooterP.ctrlGate(y, a);
        shooterN.ctrlGate(u, d);
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
        l = gamepad2.dpad_left;
        r = gamepad2.dpad_right;
        y = gamepad2.y;
        a = gamepad2.a;
        u = gamepad2.dpad_up;
        d = gamepad2.dpad_down;
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
                        -lSY, -lSX, -rSX,
                        follower.pose().heading());
                follower.manual(powers);
                break;
        }

        Pose pose = follower.pose();
        //TODO: make this changed based off of team and hive position


        aprilTag.setHiveTip();

        double targetX = Robot.Infoz.alliance == Robot.alliances.RED_ALLIANCE ? 58 : 83.5;
        double targetY = aprilTag.currentTip == hiveTip.AUDIENCE_SIDE_UP ? 90.5 : 51;

        telemetry.addData("tip", aprilTag.currentTip);

        if(aimButton) {

            //Aim code will overwrite powers
            double angle = Math.atan2(targetY-pose.y(), targetX-pose.x());
            telemetry.addData("heading angle", angle);
            powers = ManualDrive.headingLock(follower, Controller.proportional(.75), powers, angle);
        }
        ManualDrive.driveOrHold(follower, powers);

        field.moveCursor(pose.x(), pose.y());
        field.circle(3);
        field.line(targetX, targetY);
        field.update();

        follower.update();
    }
}
