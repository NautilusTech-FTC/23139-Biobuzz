package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous
public class RobotConfig extends OpMode {
    Robot.alliances alSelect;
    Robot.drives dSelect;
    Robot.startPositions sSelect;
    Robot.autos auSelect;

    Robot.alliances[] alliances = Robot.alliances.values();
    Robot.drives[] drives = Robot.drives.values();
    Robot.startPositions[] startPositions = Robot.startPositions.values();
    Robot.autos[] autos = Robot.autos.values();

    int horizontalSelection = 0;
    int verticalSelection = 0;

    @Override
    public void init() {

    }

    @Override
    public void init_loop() {
        changeSelectionArea();
        loopThroughOptions();

        if (verticalSelection == 0) {
            telemetry.addData("> alliance: ", alSelect);
        } else {
            telemetry.addData("alliance: ", alSelect);
        }

        if (verticalSelection == 1) {
            telemetry.addData("> drive: ", dSelect);
        } else {
            telemetry.addData("drive: ", dSelect);
        }

        if (verticalSelection == 2) {
            telemetry.addData("> start position: ", sSelect);
        } else {
            telemetry.addData("start position: ", sSelect);
        }

        if (verticalSelection == 3) {
            telemetry.addData("> auto: ", auSelect);
        } else {
            telemetry.addData("auto: ", auSelect);
        }

        if (verticalSelection == 4) {
            telemetry.addData("Selection complete!", "Press START to submit or B to restart");
            if (gamepad1.b) {
                verticalSelection = 0;
            }
        }
    }

    @Override
    public void start() {
        Robot.Infoz.alliance = alSelect;
        Robot.Infoz.drive = dSelect;
        Robot.Infoz.startPosition = sSelect;
        Robot.Infoz.auto = auSelect;
        telemetry.addData("Everything's submitted!", "Next match is gonna be great!");
    }

    @Override
    public void loop() {

    }

    void loopThroughOptions () {
        if (gamepad1.dpad_right) {
            horizontalSelection++;
        }
        if (gamepad1.dpad_left) {
            horizontalSelection--;
        }


        switch (verticalSelection) {
            case 0:
                if (horizontalSelection < 0) {
                    horizontalSelection = alliances.length - 1;
                }
                alSelect = alliances[(horizontalSelection % alliances.length)];
                break;
            case 1:
                if (horizontalSelection < 0) {
                    horizontalSelection = drives.length - 1;
                }
                dSelect = drives[(horizontalSelection % drives.length)];
                break;
            case 2:
                if (horizontalSelection < 0) {
                    horizontalSelection = startPositions.length - 1;
                }
                sSelect = startPositions[(horizontalSelection % startPositions.length)];
                break;
            case 3:
                if (horizontalSelection < 0) {
                    horizontalSelection = autos.length - 1;
                }
                auSelect = autos[(horizontalSelection % autos.length)];
                break;
        }
    }

    void changeSelectionArea () {
        if (gamepad1.aWasPressed()) {
            horizontalSelection = 0;
            if (verticalSelection < 4) {
                verticalSelection++;
            }
        }
    }
}
