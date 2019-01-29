package net.kullmar.bots.agility.courses;

import com.runemate.game.api.hybrid.entities.GameObject;
import net.kullmar.bots.agility.AgilityState;

public interface CourseLogic {
    AgilityState getCurrentState();
    AgilityState getState(Class<? extends AgilityState> agilityState);
    GameObject getNextObstacle();
    void updateState(Class<? extends AgilityState> state);
}
