package net.kullmar.bots.orebuyer.branches;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

public class Root extends BranchTask {
    private InventoryFullBranch bankBranch = new InventoryFullBranch();
    private InventoryEmptyBranch inventoryEmptyBranch = new InventoryEmptyBranch();

    @Override
    public TreeTask failureTask() {
        return inventoryEmptyBranch;
    }

    @Override
    public boolean validate() {
        return Inventory.isFull();
    }

    @Override
    public TreeTask successTask() {
        return bankBranch;
    }
}
