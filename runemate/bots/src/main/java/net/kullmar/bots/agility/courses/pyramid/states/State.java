package net.kullmar.bots.agility.courses.pyramid.states;

import net.kullmar.bots.agility.courses.CourseLogic;

public abstract class State {
    CourseLogic courseLogic;

    public State(CourseLogic courseLogic) {
        this.courseLogic = courseLogic;
    }

    public abstract void update();

    public boolean validate() {
        return true;
    }
}
