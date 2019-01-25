package net.kullmar.bots.agility.courses.pyramid.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.courses.pyramid.RMPyramidInfo;

import static net.kullmar.bots.agility.courses.pyramid.states.PyramidState.WAITING_STATE;

public class WalkingState extends State {
    public WalkingState(CourseLogic courseLogic) {
        super(courseLogic);
    }

    @Override
    public void update() {
        Area approachingArea = null;
        if (shouldPlayerMoveToFirstWaitingArea()) {
            approachingArea = RMPyramidInfo.WAITING_AREA_BEFORE_FIRST_MOVING_BLOCK;
        }
        RegionPath path = RegionPath.buildTo(approachingArea);
        if (path == null) {
            Environment.getLogger().debug("Failed to build path to waiting area");
            return;
        }
        if (path.step()) {
            Environment.getLogger().debug("Moving towards waiting area");
            if (Execution.delayUntil(this::isPlayerInWaitingArea, 10000)) {
                Environment.getLogger().debug("Arrived at waiting area");
                courseLogic.updateState(WAITING_STATE);
                return;
            }
            Environment.getLogger().debug("Might be stuck");
        }
    }

    private boolean shouldPlayerMoveToFirstWaitingArea() {
        return Players.getLocal() != null && RMPyramidInfo.AREA_BEFORE_FIRST_MOVING_BLOCK.contains(Players.getLocal().getPosition());
    }

    private boolean shouldPlayerMoveToSecondWaitingArea() {
        return Players.getLocal() != null && RMPyramidInfo.AREA_BEFORE_FIRST_MOVING_BLOCK.contains(Players.getLocal().getPosition());
    }

    private boolean isPlayerInWaitingArea() {
        return Players.getLocal() != null && RMPyramidInfo.WAITING_AREA_BEFORE_FIRST_MOVING_BLOCK.contains(Players.getLocal().getPosition());
    }

    public boolean validate() {
        Player localPlayer = Players.getLocal();
        if (localPlayer == null) {
            return false;
        }
        return isPlayerWalkingPastMovingBoulder();
    }

    private boolean isPlayerWalkingPastMovingBoulder() {
        return shouldPlayerMoveToFirstWaitingArea() || shouldPlayerMoveToSecondWaitingArea();
    }
}
