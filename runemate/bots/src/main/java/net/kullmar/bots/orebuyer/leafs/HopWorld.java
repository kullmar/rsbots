package net.kullmar.bots.orebuyer.leafs;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.RuneScape;
import com.runemate.game.api.hybrid.local.WorldOverview;
import com.runemate.game.api.hybrid.local.Worlds;
import com.runemate.game.api.hybrid.local.hud.interfaces.WorldHop;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;
import net.kullmar.bots.orebuyer.OreBuyerBot;

public class HopWorld extends LeafTask {
    private static final int NUMBER_OF_WORLDS_TO_ROTATE_BETWEEN = 10;

    private final OreBuyerBot bot = (OreBuyerBot) Environment.getBot();

    public HopWorld(WalkToOrdan walkTask) {
        this.walkTask = walkTask;
    }

    public HopWorld() {
    }

    private WalkToOrdan walkTask;

    private int worldHopCount = 0;
    private int startingWorld = Worlds.getCurrent();

    @Override
    public void execute() {
        int nextWorldId = getNextMembersWorld();
        if (!WorldHop.hopTo(nextWorldId)) {
            getLogger().debug("Failed to hop world");
            return;
        }
        getLogger().debug("Hopping to world: " + nextWorldId);
        if (Execution.delayUntil(() -> RuneScape.isLoggedIn() && Worlds.getCurrent() == nextWorldId, 5000)) {
            bot.setFreshWorld(true);
            if (walkTask != null) {
                walkTask.resetWalkingState();
            }
        }
    }

    private int getNextMembersWorld() {
        if (worldHopCount < NUMBER_OF_WORLDS_TO_ROTATE_BETWEEN) {
            int i = 1;
            WorldOverview nextWorld;
            do {
                nextWorld = Worlds.getOverview(Worlds.getCurrent() + i);
                if (nextWorld == null) {
                    throw new RuntimeException("Failed to get World overview");
                }
                ++i;
            } while (!isWorldValid(nextWorld));
            ++worldHopCount;
            return nextWorld.getId();
        }
        worldHopCount = 0;
        return startingWorld;
    }

    private boolean isWorldValid(WorldOverview worldOverview) {
        return worldOverview.isMembersOnly() &&
                !worldOverview.isBounty() &&
                !worldOverview.isDeadman() &&
                !worldOverview.isEoCOnly() &&
                !worldOverview.isHighRisk() &&
                !worldOverview.isLastManStanding() &&
                !worldOverview.isLegacyOnly() &&
                !worldOverview.isLootShare() &&
                !worldOverview.isPVP() &&
                !worldOverview.isSkillTotal1250() &&
                !worldOverview.isSkillTotal1500() &&
                !worldOverview.isSkillTotal1750() &&
                !worldOverview.isSkillTotal2000() &&
                !worldOverview.isSkillTotal2200() &&
                !worldOverview.isSkillTotal2600() &&
                !worldOverview.isTournament() &&
                !worldOverview.isVIP();
    }
}
