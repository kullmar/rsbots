package net.kullmar.bots.agility;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;

import java.util.concurrent.TimeUnit;

import static com.runemate.game.api.hybrid.Environment.getLogger;

public class AgilitySession implements SkillListener, InventoryListener {
    private static final int UPDATE_INTERVAL_MS = 10000;
    private long startTime;
    private int marksCount = 0;
    private int xpGained = 0;

    public void start() {
        Environment.getBot().getEventDispatcher().addListener(this);
        startTime = System.currentTimeMillis();
        new Thread(() -> {
            while (true) {
                Environment.getLogger().debug(this);
                try {
                    Thread.sleep(UPDATE_INTERVAL_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ).start();
    }

    public int getXpPerHour() {
        return Math.round(xpGained / getTimePassedInHours());
    }

    private float getTimePassedInHours() {
        return (System.currentTimeMillis() - startTime)/1000f/3600f;
    }

    @Override
    public void onItemAdded(ItemEvent event) {
        if (event.getItem().getDefinition() != null && event.getItem().getDefinition().getName().equals("Mark of grace")) {
            ++marksCount;
        }
    }

    @Override
    public void onExperienceGained(SkillEvent event) {
        if (event.getSkill() == Skill.AGILITY) {
            getLogger().debug("Completed obstacle");
        }
        xpGained += event.getChange();
    }

    @Override
    public String toString() {
        return "Session: " + getXpPerHour() + " xp/h, " + marksCount + " marks, " + " time: " +
                timePassedHhMmSs(System.currentTimeMillis() - startTime);
    }

    private String timePassedHhMmSs(long millis) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
