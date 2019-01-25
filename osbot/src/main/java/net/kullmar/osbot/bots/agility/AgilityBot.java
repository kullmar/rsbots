package net.kullmar.osbot.bots.agility;

import net.kullmar.osbot.bots.agility.courses.OSBotGenericAgilityLogic;
import net.kullmar.osbot.bots.agility.states.*;
import net.kullmar.rsbots.api.agility.AgilityState;
import net.kullmar.rsbots.api.agility.CanifisInfo;
import net.kullmar.rsbots.api.fsm.State;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@ScriptManifest(name = "Agility Bot", author = "kullmar", version = 0.1, info = "", logo = "")
public class AgilityBot extends Script {
    private Map<AgilityState, State> states = new HashMap<>();
    private State state;
    private OSBotGenericAgilityLogic courseLogic = new OSBotGenericAgilityLogic(new CanifisInfo(),
            this);
    private GroundItem markOfGrace;

    public AgilityBot() {
        initStates();
    }

    public OSBotGenericAgilityLogic getCourseLogic() {
        return courseLogic;
    }

    public GroundItem getMarkOfGrace() {
        return markOfGrace;
    }

    private void initStates() {
        states.put(AgilityState.INTERACTING_STATE, new InteractingState(this));
        states.put(AgilityState.IDLE_STATE, new IdleState(this));
        states.put(AgilityState.WALKING_STATE, new WalkingState(this));
        states.put(AgilityState.TAKING_MARK_STATE, new TakingMarkState(this));
        state = states.get(AgilityState.IDLE_STATE);
    }

    public void updateState(AgilityState stateEnum) {
        state = states.get(stateEnum);
    }

    @Override
    public void onStart() {
        //Code here will execute before the loop is started
    }

    @Override
    public void onExit() {
        //Code here will execute after the script ends
    }

    @Override
    public int onLoop() {
        updateMarkOfGrace();
        state.update();
        return ThreadLocalRandom.current().nextInt(20, 100);
    }

    private void updateMarkOfGrace() {
        markOfGrace = getGroundItems().closest(groundItem -> groundItem.getName().equals("Mark of grace") &&
                getMap().canReach(groundItem));
    }

    @Override
    public void onPaint(Graphics2D g) {
        //This is where you will put your code for paint(s)
    }
}
