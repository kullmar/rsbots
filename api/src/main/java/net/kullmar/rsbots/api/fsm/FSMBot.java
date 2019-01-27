package net.kullmar.rsbots.api.fsm;

public interface FSMBot {
    void updateState(Class<? extends State> state);
}
