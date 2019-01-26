package net.kullmar.bots.agility.courses.pyramid.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.definitions.GameObjectDefinition;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.local.hud.interfaces.Health;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import net.kullmar.bots.agility.courses.CourseLogic;

import static com.runemate.game.api.hybrid.Environment.getLogger;
import static net.kullmar.bots.agility.courses.pyramid.states.PyramidState.IDLE_STATE;
import static net.kullmar.bots.api.Interaction.interactWithAndTurnCamera;

public class InteractingState extends State implements SkillListener {
    private GameObject approachedObstacle;
    private boolean isInteracting = false;
    private int lastPlane;

    public InteractingState(CourseLogic courseLogic) {
        super(courseLogic);
        Environment.getBot().getEventDispatcher().addListener(this);
        lastPlane = Players.getLocal() == null ? 0 : Players.getLocal().getPosition().getPlane();
    }

    @Override
    public void update() {
        GameObject nextObstacle = courseLogic.getNextObstacle();
        if (nextObstacle == null) {
            getLogger().debug("Unable to determine next obstacle");
            courseLogic.updateState(IDLE_STATE);
            return;
        }
        String action = getObstacleAction(nextObstacle);
        if (action == null) {
            getLogger().debug("No action found for obstacle");
            courseLogic.updateState(IDLE_STATE);
            return;
        }
        Environment.getLogger().debug("Next obstacle: " + action + " " + nextObstacle);
        if (!interactWithAndTurnCamera(nextObstacle, action)) {
            getLogger().debug("Failed to click obstacle");
            return;
        }
        isInteracting = true;
        approachedObstacle = nextObstacle;
        Player local = Players.getLocal();
        if (local == null) {
            return;
        }
        getLogger().debug("Clicked obstacle");
        if (Execution.delayWhile(this::isInteracting, local::isMoving, 1200, 1800)) {
            courseLogic.updateState(IDLE_STATE);
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
        if (hasChangedPlane()) {
            isInteracting = false;
        }

        return isInteracting;
    }

    private boolean hasChangedPlane() {
        Player local = Players.getLocal();
        if (local == null) {
            return false;
        }
        boolean differentPlane = false;
        if (local.getPosition().getPlane() != lastPlane) {
            differentPlane = true;
        }
        lastPlane = local.getPosition().getPlane();
        return differentPlane;
    }

    @Override
    public void onExperienceGained(SkillEvent event) {
        if (isInteracting && event.getSkill() == Skill.AGILITY) {
            getLogger().debug("Completed obstacle");
            // courseLogic.setLastUsedObstacle(approachedObstacle);
        }
        isInteracting = false;
    }
}
