package net.kullmar.bots.agility.courses.pyramid.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.script.Execution;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;

import java.util.concurrent.ThreadLocalRandom;

public class IdleState extends AgilityState {
    public IdleState(CourseLogic courseLogic) {
        super(courseLogic);
    }

    @Override
    public void update() {
        if (ThreadLocalRandom.current().nextFloat() < 0.1f) {
            long idleTime = ThreadLocalRandom.current().nextLong(1000, 10000);
            Environment.getLogger().debug("Sleeping for " + idleTime + " ms");
            Execution.delay(idleTime);
        }
        if (courseLogic.getState(SellingState.class).validate()) {
            courseLogic.updateState(SellingState.class);
        } else if (courseLogic.getState(WalkingState.class).validate()) {
            Environment.getLogger().debug("Player is in area before moving block");
            courseLogic.updateState(WalkingState.class);
        }
        else {
            courseLogic.updateState(InteractingState.class);
        }
    }
}
