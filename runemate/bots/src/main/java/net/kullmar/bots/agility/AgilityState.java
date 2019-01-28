package net.kullmar.bots.agility;

import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.rsbots.api.fsm.State;

public abstract class AgilityState implements State {
    protected CourseLogic courseLogic;

    public AgilityState(CourseLogic courseLogic) {
        this.courseLogic = courseLogic;
    }

    public boolean validate() {
        return true;
    }
}
