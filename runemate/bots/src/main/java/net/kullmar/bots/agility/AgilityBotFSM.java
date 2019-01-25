package net.kullmar.bots.agility;

import com.runemate.game.api.script.framework.LoopingBot;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.courses.pyramid.PyramidLogic;

public class AgilityBotFSM extends LoopingBot {
    private CourseLogic courseLogic;

    @Override
    public void onStart(String... args) {
        courseLogic = new PyramidLogic();
        setLoopDelay(20, 100);
        // Mouse.setPathGenerator(Mouse.MLP_PATH_GENERATOR);
    }

    @Override
    public void onLoop() {
        courseLogic.getCurrentState().update();
    }
}
