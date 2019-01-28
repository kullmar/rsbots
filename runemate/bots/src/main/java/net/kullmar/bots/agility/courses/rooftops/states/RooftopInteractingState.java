package net.kullmar.bots.agility.courses.rooftops.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.definitions.GameObjectDefinition;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;

import static com.runemate.game.api.hybrid.Environment.getLogger;

public class RooftopInteractingState extends AgilityState implements SkillListener {
    private boolean isInteracting = false;

    public RooftopInteractingState(CourseLogic courseLogic) {
        super(courseLogic);
        Environment.getBot().getEventDispatcher().addListener(this);
    }

    @Override
    public void update() {
        GameObject nextObstacle = courseLogic.getNextObstacle();
        if (nextObstacle == null) {
            getLogger().debug("Unable to determine next obstacle");
            courseLogic.updateState(RooftopIdleState.class);
            return;
        }
        String action = getObstacleAction(nextObstacle);
        if (action == null) {
            getLogger().debug("No action found for obstacle");
            courseLogic.updateState(RooftopIdleState.class);
            return;
        }
        Environment.getLogger().debug("Next obstacle: " + action + " " + nextObstacle);
        if (!nextObstacle.interact(action)) {
            getLogger().debug("Failed to click obstacle");
            return;
        }
        isInteracting = true;
        Player local = Players.getLocal();
        if (local == null) {
            return;
        }
        getLogger().debug("Clicked obstacle");
        if (Execution.delayWhile(this::isInteracting, () -> local.isMoving() || local.getAnimationId() != -1, 2000,
                3000)) {
            courseLogic.updateState(RooftopIdleState.class);
        }
        isInteracting = false;
    }

    private String getObstacleAction(GameObject obstacle) {
        GameObjectDefinition definition = obstacle.getDefinition();
        if (definition == null || definition.getActions().size() == 0) {
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
        }
        isInteracting = false;
    }
}
