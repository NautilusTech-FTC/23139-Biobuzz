package org.firstinspires.ftc.teamcode;

import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.deadline;
import static com.pedropathing.ivy.groups.Groups.race;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Methods.DriveTrain;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class Auto extends OpMode {

    DriveTrain drive;

    //NONE OF THESE AUTOS ARE TESTED

    private final IvySubsystems ivy = new IvySubsystems();
    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose shootStart = poseFactory.of(53.8, 7.5, 90);
    private final Pose shootPos = poseFactory.of(58.81, 10.28, 90);

    //Friendly push
    private final Pose start = poseFactory.of(8.6274, 60.1099, 90);
    private final Pose path1 = poseFactory.of(8.6274, 98.6143, 180);


    //Shoot 'n park (medium complexity auto)
    private final Pose park2 = poseFactory.of(10.7658, 98.8414, 0);
    private final Pose park2Control1 = poseFactory.of(32.3829, 44.9207, 0);
    private final Pose park2Control2 = poseFactory.of(13.4541, 24.9694, 0);

    //More complicated auto (NEEDS FLOWERS)
    private final Pose gardenIntake = poseFactory.of(9.7063, 8.0261, 178.4579);
    private final Pose gardenIntakeControl1 = poseFactory.of(55.0198, 24.4081, 0);
    private final Pose gardenIntakeControl2 = poseFactory.of(36.7649, 7.1198, 0);
    private final Pose secondShoot = poseFactory.of(59.9234, 133.4216, 270);
    private final Pose secondShootControl1 = poseFactory.of(31.0234, 44.6153, 0);
    private final Pose secondShootControl2 = poseFactory.of(8.9468, 109.0901, 0);
    private final Pose secondShootControl3 = poseFactory.of(63.4901, 110.7874, 0);

    private final Pose flowerIntake = poseFactory.of(45.2126, 128.5234, 86.9874);
    private final Pose flowerIntakeControl1 = poseFactory.of(58.9153, 113.9739, 0);
    private final Pose flowerIntakeControl2 = poseFactory.of(87.0081, 105.0505, 0);
    private final Pose flowerIntakeControl3 = poseFactory.of(42.9614, 98.1646, 0);
    private final Pose park3 = poseFactory.of(9.0261, 114.2315, 90);
    private final Pose point5Control1 = poseFactory.of(49.3288, 118.5721, 0);

    private final Pose shootToParkCtrl = poseFactory.of(57.35, 116.66, 270);

    Command spinUpWait = waitMs(2000);
    Command shootPause = waitMs(5000);

    public Command mediumAuto() {
        return sequential(
                follow(follower, startToShoot()),
                deadline(
                        spinUpWait,
                        ivy.pollenOn
                        ),
                deadline (
                        shootPause,
                        ivy.pollenShoot,
                        ivy.transferOn
                ),
                ivy.pollenOff,
                ivy.transferOff,
                follow(follower, mediumPath0())
        );
    }

    public Command waitAndShoot() {
        return sequential(
                deadline(
                        spinUpWait,
                        ivy.pollenOn
                ),
                deadline (
                        shootPause,
                        ivy.pollenShoot,
                        ivy.transferOn
                ),
                ivy.pollenOff,
                ivy.transferOff,
                follow(follower, parkFromShoot())
        );
    }

    //REQUIRES FLOWER RAMP
    public Command complexAuto() {
        return sequential(
                ivy.pollenOn,
                ivy.pollenShoot,
                ivy.transferOn,
                shootPause,
                ivy.pollenOff,
                ivy.intakeOn,
                follow(follower, complexPath0()),
                ivy.intakeOff,
                ivy.transferOff,
                follow(follower, complexPath1()),
                ivy.pollenOn,
                ivy.pollenShoot,
                ivy.transferOn,
                shootPause,
                ivy.pollenOff,
                ivy.intakeOn,
                ivy.flowerDown,
                follow(follower, complexPath2()),
                ivy.transferOff,
                ivy.intakeOff,
                follow(follower, complexPath3()),
                ivy.flowerUp
        );
    }
    @Override
    public void init () {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(shootStart);
        ivy.init(hardwareMap);
        if (Robot.Infoz.auto == Robot.autos.PUSH_OTHER_BOT) {
            schedule(follow(follower, simplePath0()));
        }
        if (Robot.Infoz.auto == Robot.autos.SHOOT_AND_PUSH) {
            schedule(mediumAuto());
        }
        if (Robot.Infoz.auto == Robot.autos.WAIT_AND_SHOOT) {
            schedule(waitAndShoot());
        }
    }

    public void run () {

    }

    @Override
    public void loop () {
        Scheduler.execute();
        follower.update();
    }

    @Override
    public void stop () {
        if (Robot.Infoz.alliance == Robot.alliances.BLUE_ALLIANCE) { //Invert pose if blue
            Robot.Infoz.autoEndPose = poseFactory.of(141.5,141.5, 180).minus(follower.pose());
        } else {
            Robot.Infoz.autoEndPose = follower.pose();
        }

    }

    public Path parkFromShoot() {
        return Paths.curve(secondShoot, shootToParkCtrl, park3);
    }

    public Path simplePath0() {
        return Paths.line(start, path1).constant(path1);
    }

    public Path startToShoot() { return Paths.line(shootStart, shootPos).constant(shootPos); }

    public Path mediumPath0() {
        return Paths.curve(shootStart, park2Control1, park2Control2, park2).constant(park2);
    }

    public Path complexPath0() {
        return Paths.curve(shootStart, gardenIntakeControl1, gardenIntakeControl2, gardenIntake).tangent();
    }

    public Path complexPath1() {
        return Paths.curve(gardenIntake, secondShootControl1, secondShootControl2, secondShootControl3, secondShoot).linear(gardenIntake, secondShoot);
    }

    public Path complexPath2() {
        return Paths.curve(secondShoot, flowerIntakeControl1, flowerIntakeControl2, flowerIntakeControl3, flowerIntake).tangent();
    }

    public Path complexPath3() {
        return Paths.curve(flowerIntake, point5Control1, park3).constant(park3);
    }
}
