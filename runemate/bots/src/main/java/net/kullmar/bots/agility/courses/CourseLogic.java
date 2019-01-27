package net.kullmar.bots.agility.courses;

import com.runemate.game.api.hybrid.entities.GameObject;
import net.kullmar.bots.agility.courses.pyramid.states.AgilityState;

public interface CourseLogic {
    AgilityState getCurrentState();

    AgilityState getState(Class<? extends AgilityState> agilityState);
    GameObject getNextObstacle();
    void setLastUsedObstacle(GameObject gameObject);

    void updateState(Class<? extends AgilityState> state);
}
