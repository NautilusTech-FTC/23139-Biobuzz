package org.firstinspires.ftc.teamcode;

import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class Auto extends OpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose shootStart = poseFactory.of(56, 8, 90);

    //More complicated auto
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

    public void init () {}
    public void loop () {}
/*
    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path2()),
                follow(follower, path3()),
                follow(follower, path4()),
                follow(follower, path5())
        );
    }
    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(shootStart);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path2() {
        return Paths.curve(path1, gardenIntakeControl1, gardenIntakeControl2, gardenIntake).tangent();
    }

    public Path path3() {
        return Paths.curve(point3Start, secondShootControl1, secondShootControl2, secondShootControl3, secondShoot).linear(point3Start, secondShoot);
    }

    public Path path4() {
        return Paths.curve(secondShoot, flowerIntakeControl1, flowerIntakeControl2, flowerIntakeControl3, flowerIntake).tangent();
    }

    public Path path5() {
        return Paths.curve(flowerIntake, point5Control1, park3).constant(park3);
    }*/
}
