package org.firstinspires.ftc.teamcode.Testing;

import com.fasterxml.jackson.jr.ob.JSON;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class flywheelPGrapher extends LinearOpMode {
    private DcMotorEx flywheel;
    private VoltageSensor ctrlHubVoltage;

    private double lastPower;

    public static double startingValue = 12;
    public static double increment = 1;

    public static int averageRange = 5; //Increase if data is innacurate/it doesn't have enough time to spin up


    @Override
    public void runOpMode() {
        ctrlHubVoltage = hardwareMap.get(VoltageSensor.class, "Control Hub");
        File json = new File(AppUtil.FIRST_FOLDER + "/json/test.json");

        StringBuilder data = new StringBuilder();

        double currentTest = startingValue;

        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();

        double testStarted = getRuntime();

        ArrayList<Double> velocities = new ArrayList<Double>(6);

        int extraLoops = -1;

        while (currentTest > 0) {
            double runtime = getRuntime();
            runShooterAtVolts(currentTest);

            double velocity = flywheel.getVelocity();

            velocities.add(velocity);
            if (velocities.size() == (averageRange + 1)) {
                velocities.remove(0);
            }

            double deltaVelocity = (velocity - velocities.get(0));
            double averageVelocity = velocities.stream().mapToDouble(Double::doubleValue).average().orElse(velocity);

            if (deltaVelocity < 20 && extraLoops < 0) {
                extraLoops = averageRange;
            }
            if (extraLoops > 0) {
                extraLoops -= 1;
            }

            if (runtime-testStarted>1 && extraLoops == 0) { //test concludes
                try { //make json
                    data.append(JSON.std
                            .with(JSON.Feature.PRETTY_PRINT_OUTPUT)
                            .composeString()
                            .startObject()
                            .put(String.format("%.2f", currentTest), averageVelocity)
                            .end()
                            .finish());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                currentTest -= increment;
                testStarted = getRuntime();
            }
        }
        try {
            JSON.std.write(data, json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void runShooterAtVolts(double volts) {
        double hubVolts = ctrlHubVoltage.getVoltage();
        double power = volts/hubVolts;
        if (power != lastPower) {
            flywheel.setPower(power);
            lastPower = power;
        }

    }


}
