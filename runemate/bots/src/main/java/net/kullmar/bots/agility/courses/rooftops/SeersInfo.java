package net.kullmar.bots.agility.courses.rooftops;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;

public class SeersInfo implements RMRooftopInfo {
    private static final Area STARTING_AREA = new Area.Rectangular(new Coordinate(2727, 3484, 0), new Coordinate(2730,
                                                                                                                     3485, 0));
    private static final String[] OBSTACLE_NAMES = new String[] { "Wall", "Gap", "Tightrope", "Edge" };

    @Override
    public Area getStartingArea() {
        return STARTING_AREA;
    }

    @Override
    public String[] getObstacleNames() {
        return OBSTACLE_NAMES;
    }

    @Override
    public String[] getObstacleActions() {
        return new String[0];
    }
}
