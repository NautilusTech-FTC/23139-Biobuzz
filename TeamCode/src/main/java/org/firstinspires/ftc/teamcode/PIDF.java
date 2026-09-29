package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDF {
    private DcMotorEx motor;

    private double p;
    private double i;
    private double d;
    private double f;

    private double error;
    private double lastError;

    private double target;
    private double lastTarget;

    private double integral;
    private double integralLim;

    private double derivative;

    private double output;

    ElapsedTime timer = new ElapsedTime();

    public PIDF(HardwareMap hardwareMap, String motor, DcMotorEx.RunMode runMode) {
        this.motor = hardwareMap.get(DcMotorEx.class, motor);
        this.motor.setMode(runMode);
    }

    public void setPIDF(double p, double i, double d, double f) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public void setIntegralLim(double limit) {
        integralLim = limit;
    }

    public double getVelocity() {
        return(motor.getVelocity());
    }

    public void runPIDF(double value) {
        error = target - value;
        derivative = (error - lastError) / timer.seconds();
        integral = integral + (error * timer.seconds());

        if (integral > integralLim) {
            integral = integralLim;
        }
        if (integral < -integralLim) {
            integral = -integralLim;
        }

        if (target != lastTarget) {
            integral = 0;
        }

        output = (p * error) + (i * integral) + (d * derivative) + (f * target);
        motor.setPower(output);

        lastError = error;
        lastTarget = target;

        timer.reset();
    }

}
