package net.kullmar.bots.agility.courses.pyramid.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.Execution;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.courses.pyramid.RMPyramidInfo;

import static net.kullmar.bots.agility.courses.pyramid.states.PyramidState.IDLE_STATE;
import static net.kullmar.bots.agility.courses.pyramid.states.PyramidState.INTERACTING_STATE;

public class WaitingState extends State {
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
        if (Execution.delayUntil(() -> RMPyramidInfo.FIRST_MOVING_BLOCK_BLOCKING_AREA.containsAllOf(movingBlock),
                10000)) {
            Environment.getLogger().debug("Block is in blocking position - changing to interacting state");
            courseLogic.updateState(INTERACTING_STATE);
            return;
        }
        courseLogic.updateState(IDLE_STATE);
    }
}
