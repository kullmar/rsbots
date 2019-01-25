package net.kullmar.bots.agility.courses.pyramid;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import net.kullmar.bots.agility.courses.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.courses.pyramid.states.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static net.kullmar.bots.agility.courses.pyramid.RMPyramidInfo.AREA_OBSTACLE_MAPPING;
import static net.kullmar.bots.agility.courses.pyramid.RMPyramidInfo.AREA_PER_OBSTACLE;

public class PyramidLogic implements CourseLogic {
    private Map<PyramidState, State> states = new HashMap<>();
    private State currentState;
    private GameObject lastUsedObstacle;

    public PyramidLogic() {
        states.put(PyramidState.IDLE_STATE, new IdleState(this));
        states.put(PyramidState.INTERACTING_STATE, new InteractingState(this));
        states.put(PyramidState.WAITING_STATE, new WaitingState(this));
        states.put(PyramidState.WALKING_STATE, new WalkingState(this));
        currentState = states.get(PyramidState.IDLE_STATE);
    }

    public void update() {
        currentState.update();
    }

    @Override
    public State getCurrentState() {
        return currentState;
    }

    @Override
    public State getState(AgilityState agilityState) {
        return states.get(agilityState);
    }

    @Override
    public GameObject getNextObstacle() {
        Area currentArea = getCurrentObstacleArea();
        if (currentArea == null) {
            Environment.getLogger().debug("Unable to determine current obstacle area at " + Objects.requireNonNull(Players.getLocal()).getPosition());
            return null;
        }
        return GameObjects.newQuery().within(currentArea).ids(AREA_OBSTACLE_MAPPING.get(currentArea).getId())
                .results().nearest();
//        return GameObjects.newQuery().names(RMPyramidInfo.OBSTACLE_NAMES).surroundingsReachable().filter(
//                gameObject -> !gameObject.equals(lastUsedObstacle)).results().nearest();
    }

    private Area getCurrentObstacleArea() {
        Player local = Players.getLocal();
        if (local == null) {
            return null;
        }
        return AREA_PER_OBSTACLE.stream().filter(area -> area.overlaps(local.getArea())).findAny().orElse(null);
        // return AREA_PER_OBSTACLE.stream().filter(area -> area.contains(local, true)).findAny().orElse(null);
    }

    @Override
    public void setLastUsedObstacle(GameObject gameObject) {
        lastUsedObstacle = gameObject;
    }

    @Override
    public void updateState(AgilityState state) {
        this.currentState = states.get(state);
    }
}