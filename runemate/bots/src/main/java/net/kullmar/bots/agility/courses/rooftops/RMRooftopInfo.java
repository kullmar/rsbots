package net.kullmar.bots.agility.courses.rooftops;

import com.runemate.game.api.hybrid.location.Area;

public interface RMRooftopInfo {
    Area getStartingArea();
    String[] getObstacleNames();
    String[] getObstacleActions();
}
