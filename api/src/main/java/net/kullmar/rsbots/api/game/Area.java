package net.kullmar.rsbots.api.game;

public class Area {
    private Coordinate bottomLeft;
    private Coordinate topRight;

    public Area(Coordinate bottomLeft, Coordinate topRight) {
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
    }

    public Coordinate getBottomLeft() {
        return bottomLeft;
    }

    public Coordinate getTopRight() {
        return topRight;
    }
}
