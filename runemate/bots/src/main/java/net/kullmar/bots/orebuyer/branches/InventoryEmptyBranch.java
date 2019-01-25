package net.kullmar.bots.orebuyer.branches;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.kullmar.bots.orebuyer.leafs.CloseBank;

public class InventoryEmptyBranch extends BranchTask {
    private CloseBank closeBankTask = new CloseBank();
    private AfterBanking afterBanking = new AfterBanking();

    @Override
    public TreeTask failureTask() {
        return afterBanking;
    }

    @Override
    public boolean validate() {
        return Bank.isOpen();
    }

    @Override
    public TreeTask successTask() {
        return closeBankTask;
    }
}
