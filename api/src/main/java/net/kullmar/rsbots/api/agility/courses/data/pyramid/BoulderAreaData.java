package net.kullmar.rsbots.api.agility.courses.data.pyramid;

import net.kullmar.rsbots.api.agility.courses.data.RectangleData;

public class BoulderAreaData {
    private String id;
    private RectangleData bounds;

    public RectangleData getBounds() {
        return bounds;
    }

    public String getId() {
        return id;
    }
}
