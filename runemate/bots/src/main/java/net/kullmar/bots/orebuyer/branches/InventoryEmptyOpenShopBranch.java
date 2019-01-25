package net.kullmar.bots.orebuyer.branches;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.kullmar.bots.orebuyer.OreBuyerBot;
import net.kullmar.bots.orebuyer.leafs.BuyOres;
import net.kullmar.bots.orebuyer.leafs.CloseShop;

public class InventoryEmptyOpenShopBranch extends BranchTask {
    private static final int MIN_STACK = 90;

    private OreBuyerBot bot = (OreBuyerBot) Environment.getBot();
    private BuyOres buyTask = new BuyOres();
    private CloseShop closeShopTask = new CloseShop();

    @Override
    public TreeTask successTask() {
        return buyTask;
    }

    @Override
    public boolean validate() {
        return Shop.getQuantity(bot.getOreToBuy()) >= MIN_STACK;
    }

    @Override
    public TreeTask failureTask() {
        bot.setFreshWorld(false);
        return closeShopTask;
    }
}
