package org.firstinspires.ftc.teamcode;

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
        CLOSE,
        FAR
    }

    public enum autos {
        THREE_BALL,
        SIX_BALL,
        NINE_BALL,
        TWELVE_BALL
    }

    public static class Infoz {
        public static alliances alliance = alliances.BLUE_ALLIANCE;
        public static drives drive = drives.ROBOT_CENTRIC;
        public static startPositions startPosition = startPositions.FAR;
        public static autos auto = autos.TWELVE_BALL;
    }
}