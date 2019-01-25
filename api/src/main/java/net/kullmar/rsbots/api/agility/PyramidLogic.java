package net.kullmar.rsbots.api.agility;

import net.kullmar.rsbots.api.game.Bot;

import java.util.function.Predicate;

public class PyramidLogic<Obstacle> {
    private Bot<Obstacle> bot;
    private Obstacle lastUsedObstacle;
    private Predicate<Obstacle> nextObstaclePredicate = new Predicate<Obstacle>() {
        @Override
        public boolean test(Obstacle obstacle) {
            return bot.isReachable(obstacle) && obstacle.equals(lastUsedObstacle);
        }
    };

    public PyramidLogic(Bot<Obstacle> bot) {
        this.bot = bot;
    }

    public Obstacle getNextObstacle() {
        Obstacle reachableObstacles = bot.getObstacle(nextObstaclePredicate);
        return reachableObstacles;
    }
}
