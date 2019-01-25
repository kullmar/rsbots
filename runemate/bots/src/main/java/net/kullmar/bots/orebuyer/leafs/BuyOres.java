package net.kullmar.bots.orebuyer.leafs;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;
import net.kullmar.bots.orebuyer.OreBuyerBot;

public class BuyOres extends LeafTask {
    private OreBuyerBot bot = (OreBuyerBot) Environment.getBot();

    @Override
    public void execute() {
        Shop.buy(bot.getOreToBuy(), 50);
        if (Execution.delayUntil(Inventory::isFull, 500, 1000)) {
            getLogger().debug("Bought ores");
            bot.setFreshWorld(false);
            return;
        }
        getLogger().debug("Failed to buy ores");
    }
}
