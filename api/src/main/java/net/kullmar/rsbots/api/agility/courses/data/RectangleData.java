package net.kullmar.rsbots.api.agility.courses.data;

import net.kullmar.rsbots.api.agility.courses.data.pyramid.ShapeData;
import net.kullmar.rsbots.api.game.Coordinate;

public class RectangleData extends ShapeData {
    private int[] bottomLeft;
    private int[] topRight;

    public Coordinate getBottomLeft() {
        return new Coordinate(bottomLeft[0], bottomLeft[1]);
    }

    public Coordinate getTopRight() {
        return new Coordinate(topRight[0], topRight[1]);
    }
}
