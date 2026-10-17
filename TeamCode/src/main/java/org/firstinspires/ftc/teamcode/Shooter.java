package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private DcMotorEx shooter;

    private double targetVelocity = 1650;
    private boolean shooterOn = false;

    public Shooter(HardwareMap hardwareMap, String shooter,
                   DcMotorEx.RunMode runMode,
                   DcMotorEx.Direction direction) {
        this.shooter = hardwareMap.get(DcMotorEx.class, shooter);
        this.shooter.setMode(runMode);
        this.shooter.setDirection(direction);
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
            //do some shooting code;
        }

        if (stop && !start) {
            shooter.setVelocity(0);
            shooterOn = false;
        }

    }
}
