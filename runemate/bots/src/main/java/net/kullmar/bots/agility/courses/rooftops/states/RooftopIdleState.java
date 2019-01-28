package net.kullmar.bots.agility.courses.rooftops.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.script.Execution;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;

import java.util.concurrent.ThreadLocalRandom;

public class RooftopIdleState extends AgilityState {
    public RooftopIdleState(CourseLogic courseLogic) {
        super(courseLogic);
    }

    @Override
    public void update() {
        if (ThreadLocalRandom.current().nextFloat() < 0.1f) {
            long idleTime = ThreadLocalRandom.current().nextLong(1000, 10000);
            Environment.getLogger().debug("Sleeping for " + idleTime + " ms");
            Execution.delay(idleTime);
        }
        if (courseLogic.getState(RooftopMarkState.class).validate()) {
            courseLogic.updateState(RooftopMarkState.class);
        }
        else if (courseLogic.getNextObstacle() != null && !courseLogic.getNextObstacle().isVisible()) {
            courseLogic.updateState(RooftopWalkingState.class);
        }
        else {
            courseLogic.updateState(RooftopInteractingState.class);
        }
    }
}
