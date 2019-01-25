package net.kullmar.rsbots.api.game;

public class Coordinate {
    private int x;
    private int y;
    private int plane;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(int x, int y, int plane) {
        this(x, y);
        this.plane = plane;
    }

    public int getPlane() {
        return plane;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
