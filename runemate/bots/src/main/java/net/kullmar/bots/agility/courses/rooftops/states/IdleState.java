package net.kullmar.bots.agility.courses.rooftops.states;

import net.kullmar.bots.agility.AgilityState;
import net.kullmar.bots.agility.courses.CourseLogic;

public class IdleState extends AgilityState {
    public IdleState(CourseLogic courseLogic) {
        super(courseLogic);
    }

    @Override
    public void update() {
        if (courseLogic.getState(MarkState.class).validate()) {
            courseLogic.updateState(MarkState.class);
        }
        else if (!courseLogic.getNextObstacle().isVisible()) {
            courseLogic.updateState(WalkingState.class);
        }
        else {
            courseLogic.updateState(InteractingState.class);
        }
    }
}
