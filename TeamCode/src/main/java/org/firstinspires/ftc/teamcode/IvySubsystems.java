package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Methods.Flower;
import org.firstinspires.ftc.teamcode.Methods.Gates;
import org.firstinspires.ftc.teamcode.Methods.Intake;
import org.firstinspires.ftc.teamcode.Methods.Shooter;

public class IvySubsystems {
    private Flower flower;
    private Intake intake;
    private Intake transfer;
    private Shooter shooterP;
    private Shooter shooterN;
    private Gates gateP;
    private Gates gateN;
    public Command flowerDown;
    public Command flowerUp;
    public Command intakeOn;
    public Command intakeOff;
    public Command transferOn;
    public Command transferOff;
    public Command pollenOn;
    public Command pollenShoot;
    public Command pollenOff;
    public Command pollenRun;
    public Command nectarOn;
    public Command nectarShoot;
    public Command nectarOff;

    public void init(HardwareMap hardwareMap) {
        //Hardware Maps
        intake = new Intake(hardwareMap, "IntakeMotor", FORWARD);
        transfer = new Intake(hardwareMap, "TransferMotor", FORWARD);
        shooterP = new Shooter(hardwareMap, "PollenMotor", RUN_USING_ENCODER, REVERSE);
        shooterN = new Shooter(hardwareMap, "NectarMotor", RUN_USING_ENCODER, REVERSE);
        gateP = new Gates(hardwareMap, "PollenGate");
        gateN = new Gates(hardwareMap, "NectarGate");
        flower = new Flower(hardwareMap, "FlowerRamp", Servo.Direction.REVERSE);

        gateP.setCloseGate(0.269);
        gateP.setOpenGate(0.303);
        gateN.setCloseGate(0);
        gateN.setOpenGate(0.02);
        flower.setIntakeDown(0.6);
        flower.setIntakeUp(0);

        createCommands();
    }

    void createCommands () {
        //Flower Commands
        flowerDown = Command.build()
                .setStart(() -> {
                    flower.lift(true, false);
                })
                .setDone(() -> true);
        flowerUp = Command.build()
                .setStart(() -> {
                    flower.lift(false, true);
                })
                .setDone(() -> true);

        //Intake Commands
        intakeOn = Command.build()
                .setStart(() -> {
                    intake.intake(true, false);
                })
                .setEnd(endCondition -> {
                    intake.intake(false, false);
                })
                .setDone(() -> true);
        intakeOff = Command.build()
                .setStart(() -> {
                    intake.intake(false, false);
                })
                .setDone(() -> true);

        //Transfer Commands
        transferOn = Command.build()
                .setStart(() -> {
                    transfer.intake(true, false);
                })
                .setDone(() -> true);
        transferOff = Command.build()
                .setStart(() -> {
                    transfer.intake(false, false);
                })
                .setDone(() -> true);

        //Pollen Shooter Commands
        pollenOn = Command.build()
                .setExecute(() -> {
                    shooterP.shoot(true, false, 940);
                    gateP.ctrlGate(false, true);
                });
        pollenShoot = Command.build()
                .setStart(() -> {
                    shooterP.shoot(true, false, 940);
                    gateP.ctrlGate(true, false);
                })
                .setDone(() -> true);
        pollenOff = Command.build()
                .setStart(() -> {
                    shooterP.shoot(false, true, 1300);
                    gateP.ctrlGate(false, true);
                })
                .setDone(() -> true);

        //Nectar Shooter Commands
        nectarOn = Command.build()
                .setExecute(() -> {
                    shooterN.shoot(true, false, 1300);
                    gateN.ctrlGate(false, true);
                });
        nectarShoot = Command.build()
                .setStart(() -> {
                    shooterN.shoot(false, false, 1300);
                    gateN.ctrlGate(true, false);
                })
                .setDone(() -> true);
        nectarOff = Command.build()
                .setStart(() -> {
                    shooterN.shoot(false, true, 1300);
                    gateN.ctrlGate(false, true);
                })
                .setDone(() -> true);
    }
}
