package org.firstinspires.ftc.teamcode.Methods;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {
    private final DcMotorEx shooter;
    private final Servo gate;

    private boolean shooterOn = false;
    private double closeGate;
    private double openGate;

    public Shooter(HardwareMap hardwareMap,
                   String shooterMap, String gateMap,
                   DcMotorEx.RunMode runMode,
                   DcMotorEx.Direction direction) {
        this.shooter = hardwareMap.get(DcMotorEx.class, shooterMap);
        this.shooter.setMode(runMode);
        this.shooter.setDirection(direction);

        this.gate = hardwareMap.get(Servo.class, gateMap);
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

    public void ctrlGate(boolean open, boolean close) {
        if (open) {
            gate.setPosition(openGate);
        }

        if (close) {
            gate.setPosition(closeGate);
        }
    }
}
