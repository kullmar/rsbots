package net.kullmar.osbot.bots.agility.states;

import net.kullmar.osbot.api.Sleep;
import net.kullmar.osbot.bots.agility.AgilityBot;
import net.kullmar.rsbots.api.agility.AgilityState;
import net.kullmar.rsbots.api.fsm.State;

public class TakingMarkState implements State {
    private AgilityBot bot;

    public TakingMarkState(AgilityBot bot) {
        this.bot = bot;
    }

    @Override
    public void update() {
        if (bot.getMarkOfGrace().interact("Take")) {
            bot.logger.debug("Picking up Mark of grace");
            if (Sleep.sleepUntil(() -> !bot.getMarkOfGrace().exists(), 3000, 5000)) {
                bot.logger.debug("Picked up Mark of grace - changing state");
                bot.updateState(AgilityState.IDLE_STATE);
            }
        }
    }
}
