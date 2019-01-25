package net.kullmar.bots.orebuyer.leafs;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;
import net.kullmar.bots.orebuyer.OreBuyerBot;

public class AfterBuying extends LeafTask {
    private OreBuyerBot bot = (OreBuyerBot) Environment.getBot();

    @Override
    public void execute() {
        if (!Bank.deposit(bot.getOreToBuy(), 0)) {
            getLogger().debug("Failed to deposit ores");
            return;
        }
        getLogger().debug("Deposited ores");
        Execution.delayUntil(() -> Inventory.getQuantity() == 1, 200, 500);
    }
}
