package net.kullmar.rsbots.api.agility;

public abstract class AgilityLogic<Obstacle> {
    protected CourseInfo courseInfo;
    protected Obstacle lastObstacle;

    public AgilityLogic(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public Obstacle getLastObstacle() {
        return lastObstacle;
    }

    public void setLastObstacle(Obstacle lastObstacle) {
        this.lastObstacle = lastObstacle;
    }

    public abstract Obstacle getNextObstacle();
}
