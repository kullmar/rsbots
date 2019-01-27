package net.kullmar.bots.smithing.states;

import net.kullmar.bots.smithing.SmithingBot;
import net.kullmar.rsbots.api.fsm.State;

public abstract class AbstractSmithingState implements State {
    protected SmithingBot smithingBot;

    public AbstractSmithingState(SmithingBot smithingBot) {
        this.smithingBot = smithingBot;
    }
}
