package net.kullmar.bots.agility;

import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.script.framework.LoopingBot;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.courses.pyramid.PyramidLogic;
import net.kullmar.bots.agility.courses.rooftops.RooftopLogic;

public class AgilityBotFSM extends LoopingBot {
    private AgilitySession session = new AgilitySession();
    private CourseLogic courseLogic;

    @Override
    public void onStart(String... args) {
        courseLogic = new RooftopLogic();
        session.start();
        setLoopDelay(200, 400);
        Mouse.setPathGenerator(Mouse.MLP_PATH_GENERATOR);
    }

    @Override
    public void onLoop() {
        courseLogic.getCurrentState().update();
    }
}
