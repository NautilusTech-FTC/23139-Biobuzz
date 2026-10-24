package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;

public class Robot {
    public enum alliances {
        RED_ALLIANCE,
        BLUE_ALLIANCE
    }

    public enum drives {
        ROBOT_CENTRIC,
        FIELD_CENTRIC
    }

    public enum startPositions {
        NEXT_TO_PARK,
        SHOOT_POSITION
    }

    public enum autos {
        PUSH_OTHER_BOT,
        SHOOT_AND_PUSH,
        SHOOT_FLOWER_SHOOT
    }

    public static class Infoz {
        public static alliances alliance = alliances.BLUE_ALLIANCE;
        public static drives drive = drives.ROBOT_CENTRIC;
        public static startPositions startPosition = startPositions.SHOOT_POSITION;
        public static autos auto = autos.PUSH_OTHER_BOT;
        public static Pose autoEndPose = new Pose(0,0,0);
    }
}