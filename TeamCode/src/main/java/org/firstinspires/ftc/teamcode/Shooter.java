package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {
    private final DcMotorEx shooter;
    private final Servo flap;

    private double targetVelocity = 1650;
    private boolean shooterOn = false;

    public Shooter(HardwareMap hardwareMap,
                   String shooter, String flap,
                   DcMotorEx.RunMode runMode,
                   DcMotorEx.Direction direction) {
        this.shooter = hardwareMap.get(DcMotorEx.class, shooter);
        this.shooter.setMode(runMode);
        this.shooter.setDirection(direction);

        this.flap = hardwareMap.get(Servo.class, flap);
    }

    public void setTargetVelocity(double velocity) {
        targetVelocity = velocity;
    }

    public void shoot(boolean start, boolean shoot, boolean stop) {
        if (start && !stop) {
            shooter.setVelocity(targetVelocity);
            shooterOn = true;
        }

        if (shoot && shooterOn) {

        }

        if (stop && !start) {
            shooter.setVelocity(0);
            shooterOn = false;
        }

    }
}
