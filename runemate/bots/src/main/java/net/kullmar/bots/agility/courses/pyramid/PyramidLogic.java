package net.kullmar.bots.agility.courses.pyramid;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.courses.pyramid.states.*;
import net.kullmar.rsbots.api.agility.courses.data.ObstacleData;

import java.util.Objects;

import static net.kullmar.bots.agility.courses.pyramid.RMPyramidInfo.*;

public class PyramidLogic implements CourseLogic {
    private ClassToInstanceMap<AgilityState> states = MutableClassToInstanceMap.create();
    private AgilityState currentState;

    public PyramidLogic() {
        initStates();
        currentState = states.get(PyramidIdleState.class);
    }

    private void initStates() {
        states.putInstance(PyramidIdleState.class, new PyramidIdleState(this));
        states.put(PyramidInteractingState.class, new PyramidInteractingState(this));
        states.put(PyramidWaitingState.class, new PyramidWaitingState(this));
        states.put(PyramidWalkingState.class, new PyramidWalkingState(this));
        states.put(PyramidSellingState.class, new PyramidSellingState(this));
    }

    public void update() {
        currentState.update();
    }

    @Override
    public AgilityState getCurrentState() {
        return currentState;
    }

    @Override
    public AgilityState getState(Class<? extends AgilityState> state) {
        return states.getInstance(state);
    }

    @Override
    public GameObject getNextObstacle() {
        Area currentArea = getCurrentObstacleArea();
        if (isOnTopFloor()) {
            if (!Inventory.contains("Pyramid top")) {
                return GameObjects.getLoaded("Climbing rocks").nearest();
            }
            if (currentArea == null) {
                return GameObjects.getLoaded("Gap").nearest();
            }
            return GameObjects.getLoaded("Doorway").first();
        }

        if (isOnGroundFloor()) {
            GameObject stairs = GameObjects.newQuery().names("Stairs").surroundingsReachable().results().nearest();
            if (stairs != null) {
                return stairs;
            }
            Area lowerClimbingRocksArea = NON_PYRAMID_AREAS.get("lowerClimbingRocks");
            return GameObjects.newQuery().within(lowerClimbingRocksArea).names("Climbing rocks").results().nearest();
        }

        if (currentArea == null) {
            Environment.getLogger().debug("Unable to determine current obstacle area at " + Objects.requireNonNull(Players.getLocal()).getPosition());
            return null;
        }

        ObstacleData obstacleData = AREA_OBSTACLE_MAPPING.get(currentArea);
        if (obstacleData == null) {
            Environment.getLogger().debug("Could not find obstacle for current area");
            return null;
        }

        if (obstacleData.getName().equals("Stairs")) {
            return GameObjects.newQuery().names("Stairs").actions("Climb-up").results().nearest();
        }

        return GameObjects.newQuery().within(currentArea).names(obstacleData.getName()).actions(obstacleData.getAction()).results().nearest();
    }

    private boolean isOnTopFloor() {
        return Objects.requireNonNull(Players.getLocal()).getPosition().getPlane() == 3 &&
                !GameObjects.getLoaded("Doorway").isEmpty();
    }

    private boolean isOnGroundFloor() {
        return Objects.requireNonNull(Players.getLocal()).getPosition().getPlane() == 0;
    }

    private Area getCurrentObstacleArea() {
        Player local = Players.getLocal();
        if (local == null) {
            return null;
        }
        return AREA_PER_OBSTACLE.stream().filter(area -> area.overlaps(local.getArea())).findAny().orElse(null);
    }

    @Override
    public void updateState(Class<? extends AgilityState> state) {
        this.currentState = states.getInstance(state);
    }

    @Override
    public Area getStartingArea() {
        return null;
    }
}
