package net.kullmar.bots.orebuyer.leafs;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

public class CloseBank extends LeafTask {
    @Override
    public void execute() {
        if (!Bank.close()) {
            getLogger().debug("Failed to close bank interface");
            return;
        }
        getLogger().debug("Closed bank interface");
        Execution.delayWhile(Bank::isOpen, 500, 1000);
    }
}
