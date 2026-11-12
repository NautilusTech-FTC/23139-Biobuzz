package org.firstinspires.ftc.teamcode.Methods;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private final DcMotorEx shooter;

    private boolean shooterOn = false;
    private double closeGate;
    private double openGate;

    public Shooter(HardwareMap hardwareMap,
                   String shooter,
                   DcMotorEx.RunMode runMode,
                   DcMotorEx.Direction direction) {
        this.shooter = hardwareMap.get(DcMotorEx.class, shooter);
        this.shooter.setMode(runMode);
        this.shooter.setDirection(direction);
    }

    public void setCloseGate(double closePos) {
        closeGate = closePos;
    }

    public void setOpenGate(double openPos) {
        openGate = openPos;
    }

    public double getShooterVelocity() {
        return(shooter.getVelocity());
    }

    public void shoot(boolean start, boolean stop, double velocity) {
        if (start && !stop) {
            shooter.setVelocity(velocity);
            shooterOn = true;
        }

        if (stop && !start) {
            shooter.setVelocity(0);
            shooterOn = false;
        }

    }
}
