package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDClamping {
    private DcMotor motor;

    private double p;
    private double i;
    private double d;

    private double error;
    private double lastError;

    private double target;
    private double lastTarget;

    private double integral;

    private double derivative;

    private double maxOut;
    private double minOut;
    private double rawOut;
    private double output;

    ElapsedTime timer = new ElapsedTime();

    public PIDClamping(HardwareMap hardwareMap, String motor, DcMotor.RunMode runMode) {
        this.motor = hardwareMap.get(DcMotor.class, motor);
        this.motor.setMode(runMode);
    }

    public void setPID(double p, double i, double d) {
        this.p = p;
        this.i = i;
        this.d = d;
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

    public double getPosition() {
        return(motor.getCurrentPosition());
    }

    public double getPower() {
        return(motor.getPower());
    }

    public void runPID(double value, double p, double i, double d) {
        error = target - value;
        derivative = (error - lastError) / timer.seconds();

        if (target != lastTarget) {
            integral = 0;
        }

        rawOut = (p * error) + (i * integral) + (d * derivative);

        if (rawOut >= maxOut && error > 0) {}
        else if (rawOut <= minOut && error < 0) {}
        else {integral = integral + (error * timer.seconds());}

        output = Math.max(minOut, Math.min(maxOut, rawOut));
        motor.setPower(output);

        lastError = error;
        lastTarget = target;

        timer.reset();
    }

}
