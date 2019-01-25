package net.kullmar.rsbots.api.agility.courses.data.pyramid;

public class LevelData {
    private int level;
    private int plane;
    private ObstacleAreaData[] areasByObstacle;

    public int getLevel() {
        return level;
    }

    public ObstacleAreaData[] getAreasByObstacle() {
        return areasByObstacle;
    }

    public int getPlane() {
        return plane;
    }
}
