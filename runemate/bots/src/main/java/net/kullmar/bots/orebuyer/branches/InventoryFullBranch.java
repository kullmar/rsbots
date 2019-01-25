package net.kullmar.bots.orebuyer.branches;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.kullmar.bots.orebuyer.leafs.AfterBuying;

public class InventoryFullBranch extends BranchTask {
    private AfterBuying depositOresTask = new AfterBuying();
    private OpenBankBranch openBankBranch = new OpenBankBranch();

    @Override
    public TreeTask failureTask() {
        return openBankBranch;
    }

    @Override
    public boolean validate() {
        return Bank.isOpen();
    }

    @Override
    public TreeTask successTask() {
        return depositOresTask;
    }
}
