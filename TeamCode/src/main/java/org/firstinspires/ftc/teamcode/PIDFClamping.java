package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDFClamping {
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

    private double derivative;

    private double maxOut = 1.0;
    private double minOut = -1.0;
    private double rawOut;
    private double output;

    ElapsedTime timer = new ElapsedTime();

    public PIDFClamping(HardwareMap hardwareMap, String motor, DcMotorEx.RunMode runMode) {
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

    public void setMaxOut(double maxOut) {
        this.maxOut = maxOut;
    }

    public void setMinOut(double minOut) {
        this.minOut = minOut;
    }

    public double getVelocity() {
        return(motor.getVelocity());
    }

    public void runPIDF(double value) {
        double time = timer.seconds();

        error = target - value;
        derivative = (error - lastError) / time;

        if (target != lastTarget) {
            integral = 0;
        }

        rawOut = (p * error) + (i * integral) + (d * derivative) + (f * target);

        if (rawOut >= maxOut && error > 0) {}
        else if (rawOut <= minOut && error < 0) {}
        else {integral = integral + (error * time);}

        output = Math.max(minOut, Math.min(maxOut, rawOut));
        motor.setPower(output);

        lastError = error;
        lastTarget = target;

        timer.reset();
    }

}
