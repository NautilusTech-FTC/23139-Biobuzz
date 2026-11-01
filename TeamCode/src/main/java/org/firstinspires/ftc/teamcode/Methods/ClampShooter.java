package org.firstinspires.ftc.teamcode.Methods;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Methods.PID.PIDFClamping;

public class ClampShooter {
    public PIDFClamping clamp = new PIDFClamping(); //Public cuz tuning

    private final DcMotorEx shooter;
    private final Servo gate;

    private boolean shooterOn = false;
    private double closeGate;
    private double openGate;

    public ClampShooter(HardwareMap hardwareMap,
                       String shooter, String gate,
                       DcMotorEx.RunMode runMode,
                       DcMotorEx.Direction direction) {
        this.shooter = hardwareMap.get(DcMotorEx.class, shooter);
        this.shooter.setMode(runMode);
        this.shooter.setDirection(direction);

        this.gate = hardwareMap.get(Servo.class, gate);
    }

    public void setTargetVelocity(double velocity) {
        clamp.setTarget(velocity);
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

    public void shoot(boolean start, boolean shoot, boolean stop, double target) {
        if (start && !stop) {
            clamp.setTarget(target);
            shooter.setPower(clamp.runPIDF(shooter.getVelocity()));
            shooterOn = true;
        }

        if (shoot && shooterOn) {
            gate.setPosition(openGate);
        }

        if (stop && !start) {
            clamp.setTarget(0);
            shooterOn = false;
            gate.setPosition(closeGate);
        }

    }
}
