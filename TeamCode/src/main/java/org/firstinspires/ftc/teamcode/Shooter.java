package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.PID.PIDF;

public class Shooter {
    private PIDF pidf = new PIDF();

    private final DcMotorEx shooter;
    private final Servo flap;
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
        pidf.setTarget(velocity);
    }

    public double getShooterVelocity() {
        return(shooter.getVelocity());
    }

    public void shoot(boolean start, boolean shoot, boolean stop) {
        if (start && !stop) {
            shooter.setPower(pidf.runPIDF(shooter.getVelocity()));
            shooterOn = true;
        }

        if (shoot && shooterOn) {

        }

        if (stop && !start) {
            pidf.setTarget(0);
            shooterOn = false;
        }

    }
}
