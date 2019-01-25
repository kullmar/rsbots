package net.kullmar.bots.orebuyer.leafs;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;
import net.kullmar.bots.orebuyer.OreBuyerBot;

public class OpenShop extends LeafTask {
    private OreBuyerBot bot = (OreBuyerBot) Environment.getBot();

    @Override
    public void execute() {
        Npc ordan = bot.getOrdan();
        if (ordan == null) {
            return;
        }
        if (!ordan.interact("Trade")) {
            getLogger().debug("Failed to trade with Ordan");
            return;
        }
        getLogger().debug("Trading with Ordan");
        Execution.delayUntil(Shop::isOpen, 500, 1000);
    }
}
