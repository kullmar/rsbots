package net.kullmar.rsbots.api.agility.courses.data.pyramid;

import net.kullmar.rsbots.api.agility.courses.data.RectangleData;

public class CompositeRectangleData extends ShapeData {
    private RectangleData[] rectangles;

//    public CompositeRectangleShapeData(String type) {
//        super("compositeRectangle");
//    }

    public RectangleData[] getRectangles() {
        return rectangles;
    }
}
