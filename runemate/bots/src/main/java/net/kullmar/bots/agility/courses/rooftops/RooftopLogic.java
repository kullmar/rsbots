package net.kullmar.bots.agility.courses.rooftops;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.region.GameObjects;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.courses.rooftops.states.RooftopIdleState;
import net.kullmar.bots.agility.courses.rooftops.states.RooftopInteractingState;
import net.kullmar.bots.agility.courses.rooftops.states.RooftopMarkState;
import net.kullmar.bots.agility.courses.rooftops.states.RooftopWalkingState;

public class RooftopLogic implements CourseLogic {
    private RMRooftopInfo rooftopInfo = new SeersInfo();
    private ClassToInstanceMap<AgilityState> states = MutableClassToInstanceMap.create();
    private AgilityState currentState;

    public RooftopLogic() {
        states.putInstance(RooftopIdleState.class, new RooftopIdleState(this));
        states.putInstance(RooftopInteractingState.class, new RooftopInteractingState(this));
        states.putInstance(RooftopMarkState.class, new RooftopMarkState(this));
        states.putInstance(RooftopWalkingState.class, new RooftopWalkingState(this));
        currentState = states.getInstance(RooftopIdleState.class);
    }

    @Override
    public AgilityState getCurrentState() {
        return currentState;
    }

    @Override
    public AgilityState getState(Class<? extends AgilityState> agilityState) {
        return states.getInstance(agilityState);
    }

    @Override
    public GameObject getNextObstacle() {
        return GameObjects.newQuery().names(rooftopInfo.getObstacleNames()).surroundingsReachable().results().nearest();
    }

    @Override
    public void updateState(Class<? extends AgilityState> state) {
        currentState = states.getInstance(state);
    }
}
