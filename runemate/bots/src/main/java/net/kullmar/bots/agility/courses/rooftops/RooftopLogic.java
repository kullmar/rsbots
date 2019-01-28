package net.kullmar.bots.agility.courses.rooftops;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.region.GameObjects;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.rooftops.states.IdleState;
import net.kullmar.bots.agility.courses.rooftops.states.InteractingState;
import net.kullmar.bots.agility.courses.rooftops.states.MarkState;
import net.kullmar.bots.agility.courses.rooftops.states.WalkingState;

public class RooftopLogic implements CourseLogic {
    private RMRooftopInfo rooftopInfo = new SeersInfo();
    private ClassToInstanceMap<AgilityState> states = MutableClassToInstanceMap.create();
    private AgilityState currentState;

    public RooftopLogic() {
        states.putInstance(IdleState.class, new IdleState(this));
        states.putInstance(InteractingState.class, new InteractingState(this));
        states.putInstance(MarkState.class, new MarkState(this));
        states.putInstance(WalkingState.class, new WalkingState(this));
        currentState = states.getInstance(IdleState.class);
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

    @Override
    public Area getStartingArea() {
        return rooftopInfo.getStartingArea();
    }
}
