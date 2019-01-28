package net.kullmar.bots.agility.courses.pyramid.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;

import static net.kullmar.bots.api.Interaction.interactWithAndTurnCamera;

public class PyramidSellingState extends AgilityState {
    public PyramidSellingState(CourseLogic courseLogic) {
        super(courseLogic);
    }

    @Override
    public void update() {
        if (!isNpcReachable()) {
            moveToNpc();
        } else {
            sellArtifact();
        }
    }

    private void moveToNpc() {
//        WebPath path = Traversal.getDefaultWeb().getPathBuilder().buildTo(NON_PYRAMID_AREAS.get("npcArea"));
//        if (path == null) {
//            throw new IllegalStateException("Could not build path to NPC");
//        }
//        Player local = Players.getLocal();
//        if (local == null) {
//            return;
//        }
//        if (path.step()) {
//            Environment.getLogger().debug("Walking towards NPC");
//            Execution.delayUntil(this::isNpcReachable, local::isMoving, 1200, 1800);
//        }
        GameObject climbingRocks = GameObjects.getLoaded("Climbing rocks").nearest();
        if (climbingRocks == null) {
            return;
        }
        if (interactWithAndTurnCamera(climbingRocks, "Climb")) {
            Player local = Players.getLocal();
            if (local == null) {
                return;
            }
            Execution.delayUntil(this::isNpcReachable, local::isMoving, 1400, 1800);
        }
    }

    private boolean isNpcReachable() {
        return Npcs.newQuery().names("Simon Templeton").reachable().results().first() != null;
    }

    private void sellArtifact() {
        SpriteItem artifact = Inventory.getItems("Pyramid top").first();
        Npc npc = Npcs.newQuery().names("Simon Templeton").reachable().results().first();
        if (npc == null) {
            Environment.getLogger().warn("Could not find NPC");
            courseLogic.updateState(PyramidIdleState.class);
            return;
        }
        if (artifact == null) {
            Environment.getLogger().warn("Could not find artifact in inventory");
            courseLogic.updateState(PyramidIdleState.class);
            return;
        }
        Camera.concurrentlyTurnTo(npc);
        if (!Inventory.isItemSelected()) {
            artifact.interact("Use");
        } else {
            if (npc.interact("Use", "Pyramid top" + " -> " + npc.getName())) {
                if (Execution.delayUntil(() -> !artifact.isValid(), 3000)) {
                    Environment.getLogger().debug("Sold artifact");
                    courseLogic.updateState(PyramidIdleState.class);
                }
            }
        }
    }

    @Override
    public boolean validate() {
        Player local = Players.getLocal();
        if (local == null) {
            return false;
        }
        return local.getPosition().getPlane() == 0 && Inventory.contains("Pyramid top");
    }
}
