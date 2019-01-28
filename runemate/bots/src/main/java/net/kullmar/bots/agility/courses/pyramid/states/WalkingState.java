package net.kullmar.bots.agility.courses.pyramid.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.location.navigation.basic.ViewportPath;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.courses.pyramid.RMPyramidInfo;

import java.util.Objects;

public class WalkingState extends AgilityState {
    public WalkingState(CourseLogic courseLogic) {
        super(courseLogic);
    }

    @Override
    public void update() {
        Area approachingArea = null;
        if (shouldPlayerMoveToWaitingArea()) {
            approachingArea = getWaitingArea();
        }
        if (approachingArea == null) {
            courseLogic.updateState(IdleState.class);
            return;
        }
        BresenhamPath bresenhamPath = BresenhamPath.buildTo(approachingArea.getRandomCoordinate());
        ViewportPath path = ViewportPath.convert(bresenhamPath);
        if (path == null) {
            Environment.getLogger().debug("Failed to build path to waiting area");
            courseLogic.updateState(IdleState.class);
            return;
        }
        if (path.step()) {
            Environment.getLogger().debug("Moving towards waiting area");
            Execution.delay(500, 1000); // Account for delay
            if (Execution.delayUntil(() -> !Objects.requireNonNull(Players.getLocal()).isMoving(), 10000)) {
                if (isPlayerInWaitingArea()) {
                    Environment.getLogger().debug("Arrived at waiting area");
                    courseLogic.updateState(WaitingState.class);
                }
            }
        }
    }

    private Area getWaitingArea() {
        if (RMPyramidInfo.AREA_BEFORE_FIRST_MOVING_BLOCK.contains(Players.getLocal())) {
            return RMPyramidInfo.WAITING_AREA_BEFORE_FIRST_MOVING_BLOCK;
        }
        if (RMPyramidInfo.AREA_BEFORE_SECOND_MOVING_BLOCK.contains(Players.getLocal())) {
            return RMPyramidInfo.WAITING_AREA_BEFORE_SECOND_MOVING_BLOCK;
        }
        return null;
    }

    private boolean shouldPlayerMoveToWaitingArea() {
        return Players.getLocal() != null && (RMPyramidInfo.AREA_BEFORE_FIRST_MOVING_BLOCK.contains(Players.getLocal()) ||
                RMPyramidInfo.AREA_BEFORE_SECOND_MOVING_BLOCK.contains(Players.getLocal()));
    }

    private boolean isPlayerInWaitingArea() {
        return Players.getLocal() != null && (RMPyramidInfo.WAITING_AREA_BEFORE_FIRST_MOVING_BLOCK.contains(Players.getLocal()) ||
                RMPyramidInfo.WAITING_AREA_BEFORE_SECOND_MOVING_BLOCK.contains(Players.getLocal()));
    }

    public boolean validate() {
        Player localPlayer = Players.getLocal();
        if (localPlayer == null) {
            return false;
        }
        return shouldPlayerMoveToWaitingArea();
    }
}
