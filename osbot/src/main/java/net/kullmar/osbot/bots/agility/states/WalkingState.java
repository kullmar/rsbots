package net.kullmar.osbot.bots.agility.states;

import net.kullmar.osbot.api.Sleep;
import net.kullmar.osbot.bots.agility.AgilityBot;
import net.kullmar.rsbots.api.agility.AgilityState;
import net.kullmar.rsbots.api.fsm.State;
import org.osbot.rs07.api.map.Area;

public class WalkingState implements State {
    private AgilityBot bot;
    private static final Area STARTING_AREA = new Area(3493, 3484, 3507, 3488).setPlane(0);

    public WalkingState(AgilityBot bot) {
        this.bot = bot;
    }

    @Override
    public void update() {
        if (bot.getWalking().walk(STARTING_AREA.getRandomPosition())) {
            bot.logger.debug("Walking towards tree");
            if (Sleep.sleepUntil(() -> bot.getCourseLogic().getNextObstacle().isVisible(), 5000, 7000)) {
                bot.logger.debug("Tree is visible - changing state");
                bot.updateState(AgilityState.INTERACTING_STATE);
            }
        }
    }
}
