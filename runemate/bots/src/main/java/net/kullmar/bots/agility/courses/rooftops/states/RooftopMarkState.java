package net.kullmar.bots.agility.courses.rooftops.states;

import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.script.Execution;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;

import static com.runemate.game.api.hybrid.Environment.getLogger;

public class RooftopMarkState extends AgilityState {
    public RooftopMarkState(CourseLogic courseLogic) {
        super(courseLogic);
    }

    @Override
    public void update() {
        GroundItem markOfGrace = GroundItems.newQuery().names("Mark of grace").reachable().results().first();
        if (markOfGrace == null) {
            getLogger().debug("No mark of grace reachable");
            courseLogic.updateState(RooftopIdleState.class);
            return;
        }
        if (!markOfGrace.interact("Take")) {
            getLogger().debug("Failed to take mark");
            return;
        }
        getLogger().debug("Taking mark of grace");
        if (Execution.delayWhile(markOfGrace::isValid, 4000, 5000)) {
            courseLogic.updateState(RooftopIdleState.class);
        }
    }

    public boolean validate() {
        return !GroundItems.newQuery().names("Mark of grace").reachable().results().isEmpty();
    }
}
