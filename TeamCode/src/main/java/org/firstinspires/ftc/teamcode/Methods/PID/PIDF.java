package org.firstinspires.ftc.teamcode.Methods.PID;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDF {
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

    //Tuning:
    double stepSizes[] = {10.0, 1.0, 0.1, 0.01, 0.001, 0.0001};
    int stepIndex = 1;

    ElapsedTime timer = new ElapsedTime();

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

    public double runPIDF(double value) {
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

        lastError = error;
        lastTarget = target;

        timer.reset();

        return output;
    }

    //Tuning:
    public void tunePIDF(boolean stepUp, boolean vDown1, boolean vUp1, boolean vDown2, boolean vUp2) {
        if (stepUp) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        if (vDown1) {
            f -= stepSizes[stepIndex];
        }
        if (vUp1) {
            f += stepSizes[stepIndex];
        }

        if (vDown2) {
            p -= stepSizes[stepIndex];
        }
        if (vUp2) {
            p += stepSizes[stepIndex];
        }
    }

}
