package net.kullmar.rsbots.api.agility;

public class CanifisInfo implements CourseInfo {
    private static final String[] ACTIONS = new String[] { "Climb", "Jump", "Vault" };
    private static final String[] OBSTACLES = new String[] { "Tall tree", "Gap", "Pole-vault" };

    @Override
    public String[] getObstacleActions() {
        return ACTIONS;
    }

    @Override
    public String[] getObstaclesNames() {
        return OBSTACLES;
    }
}
