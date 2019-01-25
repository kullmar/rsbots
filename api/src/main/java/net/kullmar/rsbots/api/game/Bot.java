package net.kullmar.rsbots.api.game;

import java.util.function.Predicate;

public interface Bot<Obstacle> {
    Obstacle getObstacle(Predicate predicate);
    boolean isReachable(Obstacle obstacle);
    boolean walkTo(Coordinate coordinate);
    boolean interactWith(Obstacle obstacle, String actions);
}
