package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.field.FieldManager;
import com.bylazar.field.PanelsField;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.controllers.Controller;
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

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Flower;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.PIDFShooter;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Configurable
@TeleOp
public class MainTeleOp extends OpMode {
    private DriveTrain drive;
    private Intake intake;
    private Intake transfer;
    private PIDFShooter shooterP;
    private PIDFShooter shooterN;
    private Flower flower;

    private AprilTag aprilTag = new AprilTag();

    private Follower follower;

    double strafeY, strafeX, pivotBot;

    double brakeTrigger;
    boolean intakeTrigger, outtakeTrigger;
    boolean transInTrigger, transOutTrigger;

    boolean pollenEnable, pollenDisable, nectarEnable, nectarDisable, pollenOpen, pollenClose, nectarOpen, nectarClose;
    boolean aimButton;

    boolean flowerDeploy;
    boolean flowerLift;

    public static double pollenV = 900;
    public static double nectarV = 940;

    private Robot.drives driveMode = Robot.drives.PEDRO_ROBOT_CENTRIC;
    FieldManager field = PanelsField.INSTANCE.getField();
    private final PoseFactory poseFactory = PoseFactory.degrees();

    @Override
    public void init() {
        //instance subsystems:
        drive = new DriveTrain(
                hardwareMap,
                "FrontLeft", "FrontRight", "BackRight", "BackLeft",
                REVERSE, FORWARD, FORWARD, REVERSE);

        intake = new Intake(hardwareMap, "IntakeMotor", FORWARD);
        transfer = new Intake(hardwareMap, "TransferMotor", FORWARD);
        shooterP = new PIDFShooter(hardwareMap, "PollenMotor", "PollenGate", RUN_USING_ENCODER, REVERSE);
        shooterN = new PIDFShooter(hardwareMap, "NectarMotor", "NectarGate", RUN_USING_ENCODER, REVERSE);
        flower = new Flower(hardwareMap, "FlowerRamp", Servo.Direction.REVERSE);

        //config shooters
        shooterP.setCloseGate(0.269);
        shooterP.setOpenGate(0.303);
        shooterP.pidf.setPIDF(0.00122, 0, 0.00003, 0.0004225);
        shooterN.setCloseGate(0);
        shooterN.setOpenGate(0.02);
        shooterN.pidf.setPIDF(0.0012, 0, 0, 0.0004225);
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

    @Override
    public void loop() {
        ctrlVars();
        drive();

        intake.intake(intakeTrigger, outtakeTrigger);
        transfer.intake(transInTrigger, transOutTrigger);
        shooterP.shoot(pollenEnable, pollenDisable, pollenV);
        shooterN.shoot(nectarEnable, nectarDisable, nectarV);
        shooterP.ctrlGate(pollenOpen, pollenClose);
        shooterN.ctrlGate(nectarOpen, nectarClose);
        flower.lift(flowerDeploy, flowerLift);
    }

    private void ctrlVars() {
        strafeY = gamepad1.left_stick_y;
        strafeX = gamepad1.left_stick_x;
        pivotBot = gamepad1.right_stick_x;
        brakeTrigger = gamepad1.right_trigger;
        intakeTrigger = gamepad1.left_bumper;
        outtakeTrigger = gamepad1.right_bumper;
        transInTrigger = gamepad2.left_bumper;
        transOutTrigger = gamepad2.right_bumper;
        pollenEnable = gamepad2.x;
        pollenDisable = gamepad2.b;
        nectarEnable = gamepad2.dpad_left;
        nectarDisable = gamepad2.dpad_right;
        pollenOpen = gamepad2.y;
        pollenClose = gamepad2.a;
        nectarOpen = gamepad2.dpad_up;
        nectarClose = gamepad2.dpad_down;
        flowerDeploy = gamepad1.dpad_down;
        flowerLift = gamepad1.dpad_up;

        aimButton = gamepad1.a;
    }

    private void drive() {
        DrivePowers powers = null;
        switch (driveMode) { //Switch based off of drivemode
            case ROBOT_CENTRIC:
                drive.operate(strafeY, strafeX, pivotBot, brakeTrigger);
                return; //This is so we can have pedro code below the switch statement that will not run if its set to normal robot centric

            case PEDRO_ROBOT_CENTRIC:
                powers = new DrivePowers(-strafeY, -strafeX, -pivotBot);
                break;

            case PEDRO_FIELD_CENTRIC:
                powers = ManualDrive.fieldCentric(
                        -strafeY, -strafeX, -pivotBot,
                        follower.pose().heading());
                follower.manual(powers);
                break;
        }

        Pose pose = follower.pose();

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

    @Override
    public void stop () {
        if (Robot.Infoz.alliance == Robot.alliances.BLUE_ALLIANCE) { //Invert pose if blue
            Robot.Infoz.autoEndPose = poseFactory.of(141.5,141.5, 180).minus(follower.pose());
        } else {
            Robot.Infoz.autoEndPose = follower.pose();
        }

    }
}
