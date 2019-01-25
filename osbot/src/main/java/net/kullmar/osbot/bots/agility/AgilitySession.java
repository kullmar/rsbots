package net.kullmar.osbot.bots.agility;

import org.osbot.rs07.api.ui.Skill;

import java.awt.*;

public class AgilitySession {
    private AgilityBot bot;

    public AgilitySession(AgilityBot bot) {
        this.bot = bot;
    }

    public void onStart() {
        bot.getExperienceTracker().start(Skill.AGILITY);
    }

    public void onPaint(Graphics2D graphics) {

    }

    private void paintBackground(Graphics2D graphics) {

    }
}
