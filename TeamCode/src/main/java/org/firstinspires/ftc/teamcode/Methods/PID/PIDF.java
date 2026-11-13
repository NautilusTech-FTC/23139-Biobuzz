package org.firstinspires.ftc.teamcode.Methods.PID;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDF {
    private double p = 0.0047; //p-value options (best performance - worst performance): 0.0047, 0.0046, 0.00468
    private double i;
    private double d;
    private double f = 0.0003; //f-value options: 0.0003, 0.00029, 0.0004

    private double error;
    private double lastError;

    private double target;
    private double lastTarget;

    private double integral;
    private double integralLim;

    private double derivative;

    private double output;

    //Tuning:
    double[] stepSizes = {1.0, 0.1, 0.01, 0.001, 0.0001, 0.00001};
    int stepIndex = 3;

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
    public void tunePIDF(boolean stepUp, boolean vDown1, boolean vUp1, boolean vDown2, boolean vUp2, Telemetry telemetry) {
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
            d -= stepSizes[stepIndex];
        }
        if (vUp2) {
            d += stepSizes[stepIndex];
        }

        telemetry.addData("Target Speed: ", target);
        telemetry.addData("Error: ", error);
        telemetry.addLine("------------------------------------");
        telemetry.addData("D: ", "%5f", d);
        telemetry.addData("F: ", "%5f", f);
        telemetry.addData("Step Size: ", "%5f", stepSizes[stepIndex]);
    }

}
