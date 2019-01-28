package net.kullmar.bots.agility.courses.pyramid.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.Execution;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.courses.pyramid.RMPyramidInfo;

public class WaitingState extends AgilityState {
    public WaitingState(CourseLogic courseLogic) {
        super(courseLogic);
    }

    @Override
    public void update() {
        Npc movingBlock = Npcs.getLoaded("Pyramid block").nearest();
        if (movingBlock == null) {
            Environment.getLogger().debug("Could not locate Moving block");
            return;
        }
        Environment.getLogger().debug("Waiting for block to be in blocking position");
        if (Execution.delayUntil(() -> RMPyramidInfo.FIRST_MOVING_BLOCK_BLOCKING_AREA.containsAllOf(movingBlock) ||
                RMPyramidInfo.SECOND_MOVING_BLOCK_BLOCKING_AREA.containsAllOf(movingBlock),
                10000)) {
            Environment.getLogger().debug("Block is in blocking position - changing to interacting state");
            Execution.delay(200, 1000);
            courseLogic.updateState(InteractingState.class);
            return;
        }
        courseLogic.updateState(IdleState.class);
    }
}
