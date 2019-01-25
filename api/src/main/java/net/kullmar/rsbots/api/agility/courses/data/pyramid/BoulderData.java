package net.kullmar.rsbots.api.agility.courses.data.pyramid;

public class BoulderData {
    private int id;
    private BoulderAreaData[] areas;
    private int plane;

    public int getId() {
        return id;
    }

    public BoulderAreaData[] getBoulderAreas() {
        return areas;
    }

    public int getPlane() {
        return plane;
    }
}
