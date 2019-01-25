package net.kullmar.bots.agility;

import com.runemate.game.api.script.framework.tree.TreeBot;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.kullmar.bots.agility.branches.Root;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.courses.canifis.CanifisLogic;

public class AgilityBot extends TreeBot {
    private CourseLogic courseLogic;

    public CourseLogic getCourseLogic() {
        return courseLogic;
    }

    @Override
    public void onStart(String... args) {
        courseLogic = new CanifisLogic();
        setLoopDelay(20, 50);
        // Mouse.setPathGenerator(Mouse.MLP_PATH_GENERATOR);
    }

    @Override
    public TreeTask createRootTask() {
        return new Root();
    }
}
