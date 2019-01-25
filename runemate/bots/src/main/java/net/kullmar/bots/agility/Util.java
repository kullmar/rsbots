package net.kullmar.bots.agility;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;

public class Util {
    public static Coordinate getRuneMateCoordinate(net.kullmar.rsbots.api.game.Coordinate coordinate) {
        return new Coordinate(coordinate.getX(), coordinate.getY(), coordinate.getPlane());
    }

    public static Area getRuneMateArea(net.kullmar.rsbots.api.game.Area area) {
        return new Area.Rectangular(getRuneMateCoordinate(area.getBottomLeft()),
                getRuneMateCoordinate(area.getTopRight()));
    }
}
