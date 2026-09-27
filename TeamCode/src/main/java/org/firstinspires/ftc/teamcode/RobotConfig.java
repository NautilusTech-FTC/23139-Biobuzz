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

    int selection = 0;
    int selectionThing = 0;

    @Override
    public void init() {

    }

    @Override
    public void init_loop() {
        changeSelectionArea();
        loopThroughOptions();

        if (selectionThing == 0) {
            telemetry.addData("> alliance: ", alSelect);
        } else {
            telemetry.addData("alliance: ", alSelect);
        }

        if (selectionThing == 1) {
            telemetry.addData("> drive: ", dSelect);
        } else {
            telemetry.addData("drive: ", dSelect);
        }

        if (selectionThing == 2) {
            telemetry.addData("> start position: ", sSelect);
        } else {
            telemetry.addData("start position: ", sSelect);
        }

        if (selectionThing == 3) {
            telemetry.addData("> auto: ", auSelect);
        } else {
            telemetry.addData("auto: ", auSelect);
        }

        if (selectionThing == 4) {
            telemetry.addData("Selection complete!", "Press START to submit or B to restart");
            if (gamepad1.b) {
                selectionThing = 0;
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
            selection++;
        }
        if (gamepad1.dpad_left) {
            selection--;
        }


        switch (selectionThing) {
            case 0:
                if (selection < 0) {
                    selection = alliances.length - 1;
                }
                alSelect = alliances[(selection % alliances.length)];
                break;
            case 1:
                if (selection < 0) {
                    selection = drives.length - 1;
                }
                dSelect = drives[(selection % drives.length)];
                break;
            case 2:
                if (selection < 0) {
                    selection = startPositions.length - 1;
                }
                sSelect = startPositions[(selection % startPositions.length)];
                break;
            case 3:
                if (selection < 0) {
                    selection = autos.length - 1;
                }
                auSelect = autos[(selection % autos.length)];
                break;
        }
    }

    void changeSelectionArea () {
        if (gamepad1.aWasPressed()) {
            selection = 0;
            if (selectionThing < 4) {
                selectionThing++;
            }
        }
    }
}
