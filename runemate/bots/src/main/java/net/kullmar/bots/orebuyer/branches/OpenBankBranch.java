package net.kullmar.bots.orebuyer.branches;

import com.runemate.game.api.hybrid.local.hud.interfaces.Shop;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.kullmar.bots.orebuyer.leafs.CloseShop;
import net.kullmar.bots.orebuyer.leafs.OpenBank;

public class OpenBankBranch extends BranchTask {
    private CloseShop closeShopTask = new CloseShop();
    private OpenBank openBankTask = new OpenBank();

    @Override
    public TreeTask successTask() {
        return closeShopTask;
    }

    @Override
    public boolean validate() {
        return Shop.isOpen();
    }

    @Override
    public TreeTask failureTask() {
        return openBankTask;
    }
}
