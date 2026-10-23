package org.firstinspires.ftc.teamcode.pedro;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.Shooter;

public class IvySubsystems {
    private Intake intake;
    private Intake transfer;
    private Shooter shooterP;
    private Shooter shooterN;
    public Command intakeOn;
    public Command intakeOff;
    public Command transferOn;
    public Command transferOff;
    public Command pollenOn;
    public Command pollenShoot;
    public Command pollenOff;
    public Command nectarOn;
    public Command nectarShoot;
    public Command nectarOff;

    public void init(HardwareMap hardwareMap) {
        //Hardware Maps
        intake = new Intake(hardwareMap, "IntakeMotor", FORWARD);
        transfer = new Intake(hardwareMap, "TransferMotor", FORWARD);
        shooterP = new Shooter(hardwareMap, "PollenMotor", "PollenFlap", RUN_USING_ENCODER, REVERSE);
        shooterN = new Shooter(hardwareMap, "NectarMotor", "NectarFlap", RUN_USING_ENCODER, REVERSE);

        createCommands();
    }

    void createCommands () {
        //Intake Commands
        intakeOn = Command.build()
                .setStart(() -> {
                    intake.intake(true, false);
                })
                .setEnd(endCondition -> {
                    intake.intake(false, false);
                });
        intakeOff = Command.build()
                .setStart(() -> {
                    intake.intake(false, false);
                });

        //Transfer Commands
        transferOn = Command.build()
                .setStart(() -> {
                    transfer.intake(false, true);
                })
                .setEnd(endCondition -> {
                    transfer.intake(false, false);
                });
        transferOff = Command.build()
                .setStart(() -> {
                    transfer.intake(false, false);
                });

        //Pollen Shooter Commands
        pollenOn = Command.build()
                .setExecute(() -> {
                    shooterP.shoot(true, false, false);
                });
        pollenShoot = Command.build()
                .setStart(() -> {
                    shooterP.shoot(false, true, false);
                });
        pollenOff = Command.build()
                .setStart(() -> {
                    shooterP.shoot(false, false, true);
                });

        //Nectar Shooter Commands
        nectarOn = Command.build()
                .setExecute(() -> {
                    shooterN.shoot(true, false, false);
                });
        nectarShoot = Command.build()
                .setStart(() -> {
                    shooterN.shoot(false, true, false);
                });
        nectarOff = Command.build()
                .setStart(() -> {
                    shooterN.shoot(false, false, true);
                });
    }
}
