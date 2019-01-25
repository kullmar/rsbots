package net.kullmar.bots.agility.courses.pyramid;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import net.kullmar.rsbots.api.GsonUtil;
import net.kullmar.rsbots.api.agility.CourseInfo;
import net.kullmar.rsbots.api.agility.courses.data.ObstacleData;
import net.kullmar.rsbots.api.agility.courses.data.RectangleData;
import net.kullmar.rsbots.api.agility.courses.data.pyramid.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class RMPyramidInfo {
    private static final String DATA_FILE = "net/kullmar/rsbots/api/agility/pyramid.json";

    public static final String[] OBSTACLE_NAMES;
    public static final String[] OBSTACLE_ACTIONS;
    public static final BoulderData[] BOULDER_DATA;

    public static final Area AREA_BEFORE_FIRST_MOVING_BLOCK;
    public static final Area WAITING_AREA_BEFORE_FIRST_MOVING_BLOCK;
    public static final Area FIRST_MOVING_BLOCK_BLOCKING_AREA;

    public static final Area AREA_BEFORE_SECOND_MOVING_BLOCK;
    public static final Area WAITING_AREA_BEFORE_SECOND_MOVING_BLOCK;
    public static final Area SECOND_MOVING_BLOCK_BLOCKING_AREA;

    public static final List<Area> AREA_PER_OBSTACLE = new ArrayList<>();
    public static final Map<Area, ObstacleData> AREA_OBSTACLE_MAPPING = new HashMap<>();

    static {
        InputStream jsonFile =  CourseInfo.class.getResourceAsStream("pyramid.json");
        // Resources.getAsStream(DATA_FILE);
        if (jsonFile == null) {
            throw new IllegalStateException("Unable to load " + DATA_FILE);
        }

        PyramidData courseData = GsonUtil.getGson().fromJson(new InputStreamReader(jsonFile), PyramidData.class);

        OBSTACLE_NAMES = courseData.getObstacles().getNames();
        OBSTACLE_ACTIONS = courseData.getObstacles().getActions();
        BOULDER_DATA = courseData.getBoulders();
        int plane = BOULDER_DATA[0].getPlane();

        BoulderAreaData areaBeforeBoulder = getBoulderArea(1, "areaBeforeBoulder");
        BoulderAreaData waitingArea = getBoulderArea(1, "waitingArea");
        BoulderAreaData blockingArea = getBoulderArea(1, "blockingArea");
        AREA_BEFORE_FIRST_MOVING_BLOCK = getRMAreaFromRectangle(areaBeforeBoulder.getBounds(), plane);
        WAITING_AREA_BEFORE_FIRST_MOVING_BLOCK = getRMAreaFromRectangle(waitingArea.getBounds(), plane);
        FIRST_MOVING_BLOCK_BLOCKING_AREA = getRMAreaFromRectangle(blockingArea.getBounds(), plane);

        areaBeforeBoulder = getBoulderArea(2, "areaBeforeBoulder");
        waitingArea = getBoulderArea(2, "waitingArea");
        blockingArea = getBoulderArea(2, "blockingArea");
        AREA_BEFORE_SECOND_MOVING_BLOCK = getRMAreaFromRectangle(areaBeforeBoulder.getBounds(), plane);
        WAITING_AREA_BEFORE_SECOND_MOVING_BLOCK = getRMAreaFromRectangle(waitingArea.getBounds(), plane);
        SECOND_MOVING_BLOCK_BLOCKING_AREA = getRMAreaFromRectangle(blockingArea.getBounds(), plane);

        LevelData[] levels = courseData.getPyramidLevels();
        for (LevelData level : levels) {
            for (ObstacleAreaData area : level.getAreasByObstacle()) {
                ShapeData shape = area.getShape();
                if (shape instanceof RectangleData) {
                    Area rectangle = getRMAreaFromRectangle((RectangleData) shape, level.getPlane());
                    AREA_PER_OBSTACLE.add(rectangle);
                    AREA_OBSTACLE_MAPPING.put(rectangle, area.getObstacle());
                }
                else if (shape instanceof CompositeRectangleData) {
                    Area absoluteArea = getRMAbsoluteAreaFromCompositeRectangle((CompositeRectangleData) shape,
                            level.getPlane());
                    AREA_PER_OBSTACLE.add(absoluteArea);
                    AREA_OBSTACLE_MAPPING.put(absoluteArea, area.getObstacle());
                }

            }
        }
    }

    private static Area getRMAbsoluteAreaFromCompositeRectangle(CompositeRectangleData compositeRectangleShapeData,
                                                                int plane) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (RectangleData rectangleData : compositeRectangleShapeData.getRectangles()) {
            coordinates.addAll(getRMAreaFromRectangle(rectangleData, plane).getCoordinates());
        }
        return new Area.Absolute(coordinates);
    }

    private static Area getRMAreaFromRectangle(RectangleData rectangleData, int plane) {
        return new Area.Rectangular(new Coordinate(rectangleData.getBottomLeft().getX(),
                rectangleData.getBottomLeft().getY(), plane), new Coordinate(rectangleData.getTopRight().getX(),
                rectangleData.getTopRight().getY(), plane));
    }

    private static BoulderAreaData getBoulderArea(int boulderId, String areaId) {
        BoulderData boulderData =
                Arrays.stream(BOULDER_DATA).filter(boulder -> boulder.getId() == boulderId).findAny().get();
        return Arrays.stream(boulderData.getBoulderAreas()).filter((boulderAreaData -> boulderAreaData.getId().equals(areaId)))
                .findAny()
                .get();
    }
}
