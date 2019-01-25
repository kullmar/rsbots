package net.kullmar.bots.orebuyer.branches;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.kullmar.bots.orebuyer.OreBuyerBot;
import net.kullmar.bots.orebuyer.leafs.OpenShop;

public class OpenShopBranch extends BranchTask {
    private OreBuyerBot bot = (OreBuyerBot) Environment.getBot();

    private WalkAndHopBranch walkAndHopBranch = new WalkAndHopBranch();
    private OpenShop openShopTask = new OpenShop();

    @Override
    public TreeTask successTask() {
        return openShopTask;
    }

    @Override
    public boolean validate() {
        return bot.isFreshWorld();
    }

    @Override
    public TreeTask failureTask() {
        return walkAndHopBranch;
    }
}
