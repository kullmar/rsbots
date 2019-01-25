package net.kullmar.osbot.bots.agility.states;

import net.kullmar.osbot.api.Sleep;
import net.kullmar.osbot.bots.agility.AgilityBot;
import net.kullmar.rsbots.api.agility.AgilityState;
import net.kullmar.rsbots.api.fsm.State;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;


public class InteractingState implements State {
    private AgilityBot bot;
    private int lastHp;

    public InteractingState(AgilityBot bot) {
        this.bot = bot;
        lastHp = bot.getSkills().getDynamic(Skill.HITPOINTS);
    }

    @Override
    public void update() {
        RS2Object obstacle = bot.getCourseLogic().getNextObstacle();
        if (obstacle != null && obstacle.interact(obstacle.getActions()[0])) {
            int startingXp = bot.getSkills().getExperience(Skill.AGILITY);
            bot.logger.debug("Clicked obstacle");
            if (Sleep.sleepUntil(() -> bot.getSkills().getExperience(Skill.AGILITY) > startingXp || failedObstacle(), 6000,
                    10000)) {
                if (failedObstacle()) {
                    bot.logger.debug("Failed obstacle");
                    lastHp = bot.getSkills().getDynamic(Skill.HITPOINTS);
                    bot.getCourseLogic().setLastObstacle(null);
                }
                else {
                    bot.logger.debug("Completed obstacle - changing state");
                    bot.getCourseLogic().setLastObstacle(obstacle);
                }
                bot.updateState(AgilityState.IDLE_STATE);
            }
        }
    }



    private boolean failedObstacle() {
        return bot.getSkills().getDynamic(Skill.HITPOINTS) < lastHp;
    }
}
