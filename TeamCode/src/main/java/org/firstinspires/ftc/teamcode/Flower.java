package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Flower {
    final Servo intake;

    private double intakeDown;

    private double intakeUp;

    public Flower(HardwareMap hardwareMap, String intake) {
        this.intake = hardwareMap.get(Servo.class, intake);
    }

    public void setIntakeDown(double downPos) {
        intakeDown = downPos;
    }

    public void setIntakeUp(double upPos) {
        intakeUp = upPos;
    }

    public void lift(boolean down, boolean up) {

        if (down && !up) {
            intake.setPosition(intakeDown);
        } else if (up && !down) {
            intake.setPosition(intakeUp);
        }
    }
}
