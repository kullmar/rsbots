package net.kullmar.bots.agility.courses.rooftops.states;

import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.script.Execution;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;

import static com.runemate.game.api.hybrid.Environment.getLogger;

public class WalkingState extends AgilityState {
    public WalkingState(CourseLogic courseLogic) {
        super(courseLogic);
    }

    @Override
    public void update() {
        RegionPath path = RegionPath.buildTo(courseLogic.getStartingArea());
        if (path == null) {
            getLogger().debug("Failed to build path to starting area");
            courseLogic.updateState(IdleState.class);
            return;
        }
        if (path.step()) {
            getLogger().debug("Walking towards starting area");
            if (Execution.delayUntil(() -> courseLogic.getNextObstacle().isVisible(), 1000, 3000)) {
                courseLogic.updateState(InteractingState.class);
            }
        }
    }
}
