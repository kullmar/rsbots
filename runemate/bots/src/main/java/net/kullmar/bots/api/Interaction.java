package net.kullmar.bots.api;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.local.Camera;

import static com.runemate.game.api.hybrid.Environment.getLogger;

public class Interaction {
    public static boolean interactWithAndTurnCamera(LocatableEntity interactable, String action) {
        if (interactable == null) {
            return false;
        }
//        if (!interactable.isVisible()) {
//            getLogger().debug("Object not visible - rotating camera");
//            Camera.concurrentlyTurnTo(interactable);
//            return false;
//        }
        for (int tryCount = 0; tryCount < 5; ++tryCount) {
            if (interactable.interact(action)) {
                return true;
            }
        }
        getLogger().debug("Interaction failed 5 times - rotating camera");
        Camera.concurrentlyTurnTo(interactable);
        return false;
    }
}
