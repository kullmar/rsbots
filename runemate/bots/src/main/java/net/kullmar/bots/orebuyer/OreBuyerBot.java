package net.kullmar.bots.orebuyer;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.region.Banks;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.framework.tree.TreeBot;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.kullmar.bots.orebuyer.branches.Root;

public class OreBuyerBot extends TreeBot {
    private String oreToBuy = "Iron ore";

    private Npc cachedOrdan;
    private GameObject cachedBank;
    private boolean freshWorld = true;

    public boolean isFreshWorld() {
        return freshWorld;
    }

    public void setFreshWorld(boolean freshWorld) {
        this.freshWorld = freshWorld;
    }

    @Override
    public void onStart(String... args) {
        setLoopDelay(20, 50);
    }

    @Override
    public TreeTask createRootTask() {
        return new Root();
    }

    public String getOreToBuy() {
        return oreToBuy;
    }

    public GameObject getBank() {
        if (cachedBank != null && cachedBank.isValid()) {
            getLogger().debug("Returning cached bank");
            return cachedBank;
        }
        GameObject bank = Banks.getLoadedBankChests().first();
        if (bank == null) {
            getLogger().warn("Could not find a bank nearby");
        }
        getLogger().debug("Saving bank in cache");
        cachedBank = bank;
        return bank;
    }

    public Npc getOrdan() {
        if (cachedOrdan != null && cachedOrdan.isValid()) {
            getLogger().debug("Returning cached Ordan");
            return cachedOrdan;
        }
        Npc ordan = Npcs.getLoaded("Ordan").first();
        if (ordan == null) {
            getLogger().warn("Could not find Ordan");
        }
        cachedOrdan = ordan;
        return ordan;
    }
}
