package net.kullmar.bots.orebuyer.leafs;

import com.runemate.game.api.hybrid.local.hud.interfaces.Shop;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

public class CloseShop extends LeafTask {
    @Override
    public void execute() {
        if (!Shop.close()) {
            getLogger().debug("Failed to close shop interface");
            return;
        }
        getLogger().debug("Closing shop interface...");
        Execution.delayWhile(Shop::isOpen, 500, 1000);
    }
}
