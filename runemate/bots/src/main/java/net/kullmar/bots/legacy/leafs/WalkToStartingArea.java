package net.kullmar.bots.legacy.leafs;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

public class WalkToStartingArea extends LeafTask {
    private static final Area STARTING_AREA = new Area.Rectangular(new Coordinate(3493, 3484, 0),
            new Coordinate(3507, 3488, 0));

    @Override
    public void execute() {
        RegionPath path = RegionPath.buildTo(STARTING_AREA);
        if (path == null) {
            return;
        }
        if (path.step()) {
            getLogger().debug("Walking towards tree");
            Execution.delay(3000, 5000);
            return;
        }
        getLogger().debug("Walking towards tree failed");
    }
}
