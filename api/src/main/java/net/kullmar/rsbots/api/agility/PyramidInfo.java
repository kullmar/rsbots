package net.kullmar.rsbots.api.agility;

import net.kullmar.rsbots.api.game.Area;
import net.kullmar.rsbots.api.game.Coordinate;

public class PyramidInfo {
    private static final String ARTEFACT_NAME = "Pyramid top";
    private static final String NPC_NAME = "Simon Templeton";
    private static final String ROCKS = "Climbing rocks"; // Action: Climb
    public static final String[] OBSTACLE_NAMES = new String[] {
            "Stairs", // Climb-up
            "Low wall", // Climb-over
            "Ledge", // Cross
            "Pyramid block",
            "Plank", // Cross
            "Gap", // Cross, Jump
            "Climbing rocks", // Climb (pyramid top)
            "Doorway", // Enter
    };

    public static final String[] OBSTACLE_ACTIONS = new String[] {
            "Climb-up", "Climb-over", "Cross", "Jump", "Enter"
    };

    public static final Area FIRST_MOVING_BLOCK_BLOCKING_AREA = new Area(new Coordinate(3374, 2847, 1),
            new Coordinate(3375, 2848, 1));

    public static final Area AREA_BEFORE_FIRST_MOVING_BLOCK = FIRST_MOVING_BLOCK_BLOCKING_AREA;
    public static final Area WAITING_AREA_FIRST_MOVING_BLOCK = new Area(new Coordinate(3374, 2849, 1),
            new Coordinate(3375, 2849, 1));

}
