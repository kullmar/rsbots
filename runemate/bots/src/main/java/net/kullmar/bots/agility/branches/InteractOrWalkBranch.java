package net.kullmar.bots.agility.branches;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.kullmar.bots.agility.AgilityBot;
import net.kullmar.bots.agility.leafs.InteractWithNextObstacle;
import net.kullmar.bots.agility.leafs.WalkToStartingArea;

public class InteractOrWalkBranch extends BranchTask {
    private AgilityBot bot = (AgilityBot) Environment.getBot();
    private InteractWithNextObstacle interactTask = new InteractWithNextObstacle(bot.getCourseLogic());
    private WalkToStartingArea walkTask = new WalkToStartingArea();

    @Override
    public TreeTask failureTask() {
        return walkTask;
    }

    @Override
    public boolean validate() {
        GameObject obstacle = bot.getCourseLogic().getNextObstacle();
        Player self = Players.getLocal();
        if (self == null) {
            throw new RuntimeException("Unable to get local player");
        }
        boolean result = obstacle != null &&
                bot.getCourseLogic().getNextObstacle().distanceTo(self) < 20;
        if (!result) {
            if (obstacle == null) {
                getLogger().debug("Failed to get obstacle");
            } else {
                getLogger().debug("Obstacle is farther than 20 units");
            }
            getLogger().debug("Current position: " + Players.getLocal().getPosition());
        }
        return result;
    }

    @Override
    public TreeTask successTask() {
        return interactTask;
    }
}
