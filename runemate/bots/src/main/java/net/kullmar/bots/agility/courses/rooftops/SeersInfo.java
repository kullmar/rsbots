package net.kullmar.bots.agility.courses.rooftops;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;

public class SeersInfo implements RMRooftopInfo {
    public static final Area STARTING_AREA = new Area.Rectangular(new Coordinate(2727, 3484, 0), new Coordinate(2730,
            3485, 0));
    public static final Area END_AREA = new Area.Rectangular(new Coordinate(2704, 3463, 0), new Coordinate(2704, 3464,
            0));
    public static final Coordinate[] preferredPathFromEndToStart = {
            new Coordinate(2719, 3469, 0),
            new Coordinate(2726, 3477, 0),
            new Coordinate(2728, 3485, 0)
    };
    private static final String[] OBSTACLE_NAMES = new String[] { "Wall", "Gap", "Tightrope", "Edge" };

    @Override
    public Area getStartingArea() {
        return STARTING_AREA;
    }

    @Override
    public String[] getObstacleNames() {
        return OBSTACLE_NAMES;
    }
}
