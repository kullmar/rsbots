package net.kullmar.bots.orebuyer.branches;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.kullmar.bots.orebuyer.leafs.HopWorld;
import net.kullmar.bots.orebuyer.leafs.WalkToOrdan;

public class WalkAndHopBranch extends BranchTask {
    private WalkToOrdan walkTask = new WalkToOrdan();
    private HopWorld hopTask = new HopWorld(walkTask);

    @Override
    public TreeTask successTask() {
        return hopTask;
    }

    @Override
    public boolean validate() {
        return walkTask.isWalkingOrAlreadyThere();
    }

    @Override
    public TreeTask failureTask() {
        return walkTask;
    }
}
