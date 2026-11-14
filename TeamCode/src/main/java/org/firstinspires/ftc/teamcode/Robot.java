package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;

public class Robot {
    public enum alliances {
        RED_ALLIANCE,
        BLUE_ALLIANCE
    }

    public enum drives {
        ROBOT_CENTRIC,
        PEDRO_ROBOT_CENTRIC,
        PEDRO_FIELD_CENTRIC
    }

    public enum autos {
        PUSH_OTHER_BOT,
        SHOOT_AND_PUSH,
        WAIT_AND_SHOOT
    }

    public static class Infoz {
        public static alliances alliance = alliances.BLUE_ALLIANCE;
        public static drives drive = drives.PEDRO_ROBOT_CENTRIC;
        public static autos auto = autos.SHOOT_AND_PUSH;
        public static Pose autoEndPose = new Pose(0,0,0);
    }
}