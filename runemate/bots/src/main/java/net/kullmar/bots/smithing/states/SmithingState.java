package net.kullmar.bots.smithing.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import net.kullmar.bots.smithing.SmithingBot;

import java.util.Objects;

public class SmithingState extends AbstractSmithingState {
    public SmithingState(SmithingBot smithingBot) {
        super(smithingBot);
    }

    @Override
    public void update() {
        if (!Inventory.contains(smithingBot.getBarRequired())) {
            Environment.getLogger().debug("Time to bank");
            smithingBot.updateState(BankingState.class);
            return;
        }
        if (!isInterfaceOpen()) {
            openInterface();
        } else {
            smith();
        }
    }

    private void smith() {
        InterfaceComponent interfaceItem = Interfaces.newQuery().names(smithingBot.getThingToSmith()).results().first();
        if (interfaceItem == null) {
            Environment.getLogger().debug("Could not find interface item");
            return;
        }
        if (interfaceItem.interact("Smith All sets")) {
            Environment.getLogger().debug("Smithing items");
            Execution.delayWhile(this::isSmithing, 81000);
        }
    }

    private boolean isSmithing() {
        return Objects.requireNonNull(Players.getLocal()).getAnimationId() != -1 && Inventory.contains(smithingBot.getBarRequired());
    }

    private boolean isInterfaceOpen() {
        return !Interfaces.newQuery().containers(312).results().isEmpty();
    }

    private void openInterface() {
        GameObject anvil = GameObjects.getLoaded("Anvil").nearest();
        if (anvil == null) {
            Environment.getLogger().debug("No anvils nearby");
            return;
        }
        if (anvil.interact("Smith")) {
            Environment.getLogger().debug("Opening smithing interface");
            Execution.delayUntil(this::isInterfaceOpen, 4000, 5000);
        }
    }
}
