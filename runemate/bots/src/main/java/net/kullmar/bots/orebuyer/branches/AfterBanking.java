package net.kullmar.bots.orebuyer.branches;

import com.runemate.game.api.hybrid.local.hud.interfaces.Shop;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

public class AfterBanking extends BranchTask {
    private OpenShopBranch openShopBranch = new OpenShopBranch();
    private InventoryEmptyOpenShopBranch inventoryEmptyOpenShopBranch = new InventoryEmptyOpenShopBranch();

    @Override
    public TreeTask successTask() {
        return inventoryEmptyOpenShopBranch;
    }

    @Override
    public boolean validate() {
        return Shop.isOpen();
    }

    @Override
    public TreeTask failureTask() {
        return openShopBranch;
    }
}
