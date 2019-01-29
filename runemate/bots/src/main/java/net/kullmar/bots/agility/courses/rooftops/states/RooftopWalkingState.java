package net.kullmar.bots.agility.courses.rooftops.states;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.Players;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.courses.rooftops.SeersInfo;

import static com.runemate.game.api.hybrid.Environment.getLogger;

public class RooftopWalkingState extends AgilityState {
    public RooftopWalkingState(CourseLogic courseLogic) {
        super(courseLogic);
    }

    @Override
    public void update() {
        if (courseLogic.getNextObstacle() != null && courseLogic.getNextObstacle().isVisible()) {
            courseLogic.updateState(RooftopIdleState.class);
            return;
        }
        Player local = Players.getLocal();
        if (local == null) {
            return;
        }
        Path path = RegionPath.buildTo(SeersInfo.STARTING_AREA);
        if (path == null) {
            getLogger().debug("Failed to build path to starting area");
            courseLogic.updateState(RooftopIdleState.class);
            return;
        }
        if (path.step()) {
            getLogger().debug("Walking towards starting area");
        }
    }
}
