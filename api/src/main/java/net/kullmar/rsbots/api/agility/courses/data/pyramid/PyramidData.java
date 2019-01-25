package net.kullmar.rsbots.api.agility.courses.data.pyramid;

import net.kullmar.rsbots.api.agility.courses.data.AreaData;
import net.kullmar.rsbots.api.agility.courses.data.ObstaclesData;

public class PyramidData {
    private AreaData[] areas;
    private BoulderData[] boulders;
    private ObstaclesData obstacles;
    private LevelData[] pyramidLevels;

    public AreaData[] getAreas() {
        return areas;
    }

    public BoulderData[] getBoulders() {
        return boulders;
    }

    public ObstaclesData getObstacles() {
        return obstacles;
    }

    public LevelData[] getPyramidLevels() {
        return pyramidLevels;
    }
}
