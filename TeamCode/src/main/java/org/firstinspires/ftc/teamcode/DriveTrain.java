package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveTrain {
    private final DcMotor fL;
    private final DcMotor fR;
    private final DcMotor bR;
    private final DcMotor bL;

    private double minPower = 0.1;

    public DriveTrain(
            HardwareMap hardwareMap,
            String fL, String fR, String bR, String bL,
            DcMotorSimple.Direction fLDir, DcMotorSimple.Direction fRDir, DcMotorSimple.Direction bRDir, DcMotorSimple.Direction bLDir) {
        this.fL = hardwareMap.get(DcMotor.class, fL);
        this.fR = hardwareMap.get(DcMotor.class, fR);
        this.bR = hardwareMap.get(DcMotor.class, bR);
        this.bL = hardwareMap.get(DcMotor.class, bL);
        
        this.fL.setDirection(fLDir);
        this.fR.setDirection(fRDir);
        this.bR.setDirection(bRDir);
        this.bL.setDirection(bLDir);

        this.fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setMinPower(double minPower) {
        this.minPower = minPower;
    }

    public void operate(double forward, double strafe, double rotate, double brakes) {
        double denom = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(rotate), 1);
        brakes = 1 - brakes * (1 - minPower);

        fL.setPower(((-forward + strafe + rotate) / denom) * brakes);
        fR.setPower(((-forward - strafe - rotate) / denom) * brakes);
        bR.setPower(((-forward + strafe - rotate) / denom) * brakes);
        bL.setPower(((-forward - strafe + rotate) / denom) * brakes);
    }
}
