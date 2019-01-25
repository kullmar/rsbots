package net.kullmar.bots.agility.branches;

import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.kullmar.bots.agility.leafs.TakeMarkOfGrace;

public class Root extends BranchTask {
    private GroundItem mark;
    private InteractOrWalkBranch interactOrWalkBranch = new InteractOrWalkBranch();

    @Override
    public TreeTask failureTask() {
        return interactOrWalkBranch;
    }

    @Override
    public boolean validate() {
        mark = getReachableMarkOfGrace();
        return mark != null;
    }

    @Override
    public TreeTask successTask() {
        return new TakeMarkOfGrace(mark);
    }

    private GroundItem getReachableMarkOfGrace() {
        return GroundItems.newQuery().names("Mark of grace").reachable().results().first();
    }
}
