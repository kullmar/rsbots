package net.kullmar.bots.orebuyer.leafs;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.tree.LeafTask;
import net.kullmar.bots.orebuyer.OreBuyerBot;

public class WalkToOrdan extends LeafTask {
    private OreBuyerBot bot = (OreBuyerBot) Environment.getBot();
    private Area cachedArea;

    private boolean isWalkingOrAlreadyThere = false;

    public boolean isWalkingOrAlreadyThere() {
        return isWalkingOrAlreadyThere;
    }

    @Override
    public void execute() {
        Area possibleArea = getOrdanSurrounding();
        if (possibleArea.overlaps(Players.getLocal().getArea())) {
            getLogger().debug("Already near Ordan - not walking");
            isWalkingOrAlreadyThere = true;
            return;
        }
        if (!possibleArea.getRandomCoordinate().click()) {
            getLogger().debug("Failed to walk towards Ordan");
            return;
        }
        isWalkingOrAlreadyThere = true;
    }

    private Area getOrdanSurrounding() {
        if (cachedArea != null) {
            return cachedArea;
        }
        Npc ordan = bot.getOrdan();
        Coordinate ordanLocation = ordan.getPosition();
        cachedArea = new Area.Rectangular(ordanLocation.derive(0, -2),
                ordanLocation.derive(1, 1));
        return cachedArea;
    }

    public void resetWalkingState() {
        isWalkingOrAlreadyThere = false;
    }
}
