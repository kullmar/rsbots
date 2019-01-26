package net.kullmar.bots.agility.courses.pyramid.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.script.Execution;
import net.kullmar.bots.agility.courses.CourseLogic;

import java.util.concurrent.ThreadLocalRandom;

import static net.kullmar.bots.agility.courses.pyramid.states.PyramidState.INTERACTING_STATE;
import static net.kullmar.bots.agility.courses.pyramid.states.PyramidState.WALKING_STATE;

public class IdleState extends State {
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
        if (courseLogic.getState(WALKING_STATE).validate()) {
            Environment.getLogger().debug("Player is in area before moving block");
            courseLogic.updateState(WALKING_STATE);
        }
        else {
            courseLogic.updateState(INTERACTING_STATE);
        }
    }
}
