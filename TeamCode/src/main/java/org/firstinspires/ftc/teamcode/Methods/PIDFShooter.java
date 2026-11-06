package org.firstinspires.ftc.teamcode.Methods;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Methods.PID.PIDF;

public class PIDFShooter {
    public PIDF pidf = new PIDF(); //Public cuz tuning

    private final DcMotorEx shooter;
    private final Servo gate;

    private boolean shooterOn = false;
    private double closeGate;
    private double openGate;

    public PIDFShooter(HardwareMap hardwareMap,
                   String shooter, String gate,
                   DcMotorEx.RunMode runMode,
                   DcMotorEx.Direction direction) {
        this.shooter = hardwareMap.get(DcMotorEx.class, shooter);
        this.shooter.setMode(runMode);
        this.shooter.setDirection(direction);

        this.gate = hardwareMap.get(Servo.class, gate);
    }

    public void setTargetVelocity(double velocity) {
        pidf.setTarget(velocity);
    }

    public void setCloseGate(double closePos) {
        closeGate = closePos;
    }

    public void setOpenGate(double openPos) {
        openGate = openPos;
    }

    public double getShooterVelocity() {
        return shooter.getVelocity();
    }

    public void shoot(boolean start, boolean shoot, boolean stop, double target) {
        if (start && !stop) {
            pidf.setTarget(target);
            shooter.setPower(pidf.runPIDF(getShooterVelocity()));
            shooterOn = true;
        }

        if (shoot && shooterOn) {
            gate.setPosition(openGate);
        }

        if (stop && !start) {
            pidf.setTarget(0);
            shooterOn = false;
            gate.setPosition(closeGate);
        }

    }
}
