package net.kullmar.bots.orebuyer.leafs;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;
import net.kullmar.bots.orebuyer.OreBuyerBot;

public class OpenBank extends LeafTask {
    private OreBuyerBot bot = (OreBuyerBot) Environment.getBot();

    @Override
    public void execute() {
        GameObject bank = bot.getBank();
        if (bank == null) {
            return;
        }
        if (!Bank.open(bot.getBank())) {
            getLogger().debug("Failed to open bank. Trying again shortly...");
            Execution.delay(25, 200, 50);
            return;
        }
        getLogger().debug("Opening bank...");
        Execution.delayUntil(Bank::isOpen, 4000, 5000);
    }
}
