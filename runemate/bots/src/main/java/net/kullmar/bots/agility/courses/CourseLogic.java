package net.kullmar.bots.agility.courses;

import com.runemate.game.api.hybrid.entities.GameObject;
import net.kullmar.bots.agility.courses.pyramid.states.State;

public interface CourseLogic {
    State getCurrentState();
    State getState(AgilityState agilityState);
    GameObject getNextObstacle();
    void setLastUsedObstacle(GameObject gameObject);
    void updateState(AgilityState state);
}
