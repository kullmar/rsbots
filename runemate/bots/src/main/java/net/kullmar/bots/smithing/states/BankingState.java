package net.kullmar.bots.smithing.states;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.Banks;
import com.runemate.game.api.script.Execution;
import net.kullmar.bots.smithing.SmithingBot;

public class BankingState extends AbstractSmithingState {
    public BankingState(SmithingBot smithingBot) {
        super(smithingBot);
    }

    @Override
    public void update() {
        if (!Bank.isOpen()) {
            openBank();
        } else {
            if (Inventory.contains(smithingBot.getThingToSmith())) {
                depositItems();
            } else if (!Inventory.isFull()) {
                withdrawBars();
            } else {
                if (Bank.close()) {
                    Environment.getLogger().debug("Closing bank");
                    if (Execution.delayWhile(Bank::isOpen, 1000, 2000)) {
                        Environment.getLogger().debug("Time to smith");
                        smithingBot.updateState(SmithingState.class);
                    }
                }
            }
        }
    }

    private void openBank() {
        LocatableEntity bank = Banks.getLoaded().nearest();
        if (bank == null) {
            Environment.getLogger().debug("No banks nearby");
            return;
        }
        if (Bank.open(bank)) {
            Environment.getLogger().debug("Opening bank");
            Execution.delayUntil(Bank::isOpen, 4000, 5000);
        }
    }

    private void depositItems() {
        if (Bank.deposit(smithingBot.getThingToSmith(), 0)) {
            Environment.getLogger().debug("Depositing items");
            Execution.delayUntil(() -> !Inventory.contains(smithingBot.getThingToSmith()), 1000, 2000);
        }
    }

    private void withdrawBars() {
        if (Bank.withdraw(smithingBot.getBarRequired(), 0)) {
            Environment.getLogger().debug("Withdrawing bars");
            Execution.delayUntil(Inventory::isFull, 1000, 2000);
        }
    }
}
