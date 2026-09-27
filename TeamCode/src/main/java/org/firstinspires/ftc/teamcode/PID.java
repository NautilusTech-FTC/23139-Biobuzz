package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PID {
    private DcMotor motor;

    private double p;
    private double i;
    private double d;

    private double error;
    private double lastError;

    private double target;
    private double lastTarget;

    private double integral;
    private double integralLim;

    private double derivative;

    private double output;

    ElapsedTime timer = new ElapsedTime();

    public PID(HardwareMap hardwareMap, String motor, DcMotor.RunMode runMode) {
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

    public void setIntegralLim(double limit) {
        integralLim = limit;
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

        output = (p * error) + (i * integral) + (d * derivative);
        motor.setPower(output);

        lastError = error;
        lastTarget = target;

        timer.reset();
    }

}
