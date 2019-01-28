package net.kullmar.bots.legacy.leafs;

import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

public class TakeMarkOfGrace extends LeafTask {
    private GroundItem markOfGrace;

    public TakeMarkOfGrace(GroundItem markOfGrace) {
        this.markOfGrace = markOfGrace;
    }

    @Override
    public void execute() {
        if (!markOfGrace.isValid()) {
            getLogger().debug("Mark is no longer valid");
            return;
        }
        if (!markOfGrace.interact("Take")) {
            getLogger().debug("Failed to take mark");
            return;
        }
        getLogger().debug("Taking mark of grace");
        if (Execution.delayWhile(() -> markOfGrace.isValid(), 4000, 5000)) {
            Execution.delay(200, 500);
        }
    }
}
