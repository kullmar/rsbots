package net.kullmar.osbot.bots.agility.states;

import net.kullmar.osbot.bots.agility.AgilityBot;
import net.kullmar.rsbots.api.agility.AgilityState;
import net.kullmar.rsbots.api.fsm.State;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;

import java.util.concurrent.ThreadLocalRandom;

public class IdleState implements State {
    private AgilityBot bot;

    public IdleState(AgilityBot bot) {
        this.bot = bot;
    }

    @Override
    public void update() {
        try {
            long idleTime = ThreadLocalRandom.current().nextLong(50, 1000);
            bot.logger.debug("Idling for " + idleTime + " ms");
            MethodProvider.sleep(idleTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (bot.getMarkOfGrace() != null) {
            bot.updateState(AgilityState.TAKING_MARK_STATE);
            return;
        }
        RS2Object obstacle = bot.getCourseLogic().getNextObstacle();
        if (obstacle == null) {
            return;
        }
        if (bot.getMap().distance(obstacle) > 20) {
            bot.updateState(AgilityState.WALKING_STATE);
        }
        bot.updateState(AgilityState.INTERACTING_STATE);
    }
}
