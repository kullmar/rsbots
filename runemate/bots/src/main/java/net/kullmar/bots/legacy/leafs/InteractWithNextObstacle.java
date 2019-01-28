package net.kullmar.bots.legacy.leafs;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.definitions.GameObjectDefinition;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import com.runemate.game.api.script.framework.tree.LeafTask;
import net.kullmar.bots.agility.courses.CourseLogic;

public class InteractWithNextObstacle extends LeafTask implements SkillListener {
    private CourseLogic courseLogic;

    private boolean isInteracting = false;

    public InteractWithNextObstacle(CourseLogic courseLogic) {
        this.courseLogic = courseLogic;
        Environment.getBot().getEventDispatcher().addListener(this);
    }

    @Override
    public void execute() {
        GameObject nextObstacle = courseLogic.getNextObstacle();
        String action = getObstacleAction(nextObstacle);
        if (action == null || !nextObstacle.interact(action)) {
            getLogger().debug("Failed to click obstacle");
            return;
        }
        isInteracting = true;
        getLogger().debug("Clicked obstacle");
        if (Execution.delayWhile(this::isInteracting, 6000, 10000)) {
            long idleTime = (long) Random.nextGaussian(0, 5000, 200);
            getLogger().debug("Idling " + idleTime + " ms");
            Execution.delay(idleTime);
        }
        isInteracting = false;
    }

    private String getObstacleAction(GameObject obstacle) {
        GameObjectDefinition definition = obstacle.getDefinition();
        if (definition == null) {
            return null;
        }
        return obstacle.getDefinition().getActions().get(0);
    }

    private boolean isInteracting() {
        if (hasTakenDamage()) {
            isInteracting = false;
        }
        return isInteracting;
    }

    private boolean hasTakenDamage() {
        Player self = Players.getLocal();
        return self != null && self.getHealthGauge() != null;
    }

    @Override
    public void onExperienceGained(SkillEvent event) {
        if (isInteracting && event.getSkill() == Skill.AGILITY) {
            getLogger().debug("Completed obstacle");
            isInteracting = false;
        }
    }
}
