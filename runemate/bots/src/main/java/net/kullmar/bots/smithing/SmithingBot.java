package net.kullmar.bots.smithing;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.script.framework.LoopingBot;
import net.kullmar.bots.smithing.states.AbstractSmithingState;
import net.kullmar.bots.smithing.states.BankingState;
import net.kullmar.bots.smithing.states.SmithingState;

public class SmithingBot extends LoopingBot {
    private String thingToSmith = "Iron dart tip";
    private String barRequired = "Iron bar";
    private ClassToInstanceMap<AbstractSmithingState> states = MutableClassToInstanceMap.create();
    private AbstractSmithingState activeState;

    public SmithingBot() {
        states.putInstance(SmithingState.class, new SmithingState(this));
        states.putInstance(BankingState.class, new BankingState(this));
        activeState = states.getInstance(BankingState.class);
    }

    public String getThingToSmith() {
        return thingToSmith;
    }

    public String getBarRequired() {
        return barRequired;
    }

    @Override
    public void onStart(String... args) {
        setLoopDelay(200, 400);
        Mouse.setPathGenerator(Mouse.MLP_PATH_GENERATOR);
    }

    @Override
    public void onLoop() {
        activeState.update();
    }

    public void updateState(Class<? extends AbstractSmithingState> state) {
        activeState = states.getInstance(state);
    }
}
