package net.kullmar.bots.agility.courses.pyramid.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.definitions.GameObjectDefinition;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import net.kullmar.bots.agility.courses.CourseLogic;

import java.util.Objects;

import static com.runemate.game.api.hybrid.Environment.getLogger;
import static net.kullmar.bots.agility.courses.pyramid.states.PyramidState.IDLE_STATE;

public class InteractingState extends State implements SkillListener {
    private GameObject approachedObstacle;
    private boolean isInteracting = false;

    public InteractingState(CourseLogic courseLogic) {
        super(courseLogic);
        Environment.getBot().getEventDispatcher().addListener(this);
    }

    @Override
    public void update() {
        GameObject nextObstacle = courseLogic.getNextObstacle();
        if (nextObstacle == null) {
            getLogger().debug("Unable to determine next obstacle");
            return;
        }
        String action = getObstacleAction(nextObstacle);
        Environment.getLogger().debug("Next obstacle: " + nextObstacle);
        if (action == null) {
            getLogger().debug("No action found for obstacle");
            return;
        }
        if (!nextObstacle.interact(action)) {
            getLogger().debug("Failed to click obstacle");
            return;
        }
        isInteracting = true;
        approachedObstacle = nextObstacle;
        getLogger().debug("Clicked obstacle");
        if (Execution.delayWhile(this::isInteracting, 10000)) {
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
//        if (hasTakenDamage()) {
//            isInteracting = false;
//        }
        return isInteracting;
    }

    private boolean hasTakenDamage() {
        Player self = Players.getLocal();
        return self != null && self.getHealthGauge() != null;
    }

    @Override
    public void onExperienceGained(SkillEvent event) {
        if (isInteracting && event.getSkill() == Skill.AGILITY) {
            if (Objects.requireNonNull(Players.getLocal()).distanceTo(approachedObstacle) > 3) { // Gained XP from
                // rolling stone
                getLogger().debug("Completed rolling stone");
            }
            else {
                getLogger().debug("Completed obstacle");
                courseLogic.setLastUsedObstacle(approachedObstacle);
            }
            isInteracting = false;
        }
    }
}
