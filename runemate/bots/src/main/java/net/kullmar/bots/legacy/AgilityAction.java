package net.kullmar.bots.legacy;

public enum AgilityAction {
    CLIMB("Climb"),
    JUMP("Jump"),
    VAULT("Vault");

    private String action;

    AgilityAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
