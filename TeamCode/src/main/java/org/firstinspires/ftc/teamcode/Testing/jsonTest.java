package org.firstinspires.ftc.teamcode.Testing;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;



import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.jr.ob.JSON;

@Disabled
@TeleOp
public class jsonTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        File json = new File(AppUtil.FIRST_FOLDER + "/json/test.json");
        waitForStart();

        Map<String, String> data = Map.of(
                "json", "test",
                "1", "2"
                );

        try {
            JSON.std.write(data, json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
