package net.kullmar.osbot.api;

import org.osbot.rs07.utility.ConditionalSleep;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BooleanSupplier;

public final class Sleep extends ConditionalSleep {

    private final BooleanSupplier condition;

    public Sleep(final BooleanSupplier condition, final int timeout) {
        super(timeout);
        this.condition = condition;
    }

    public Sleep(final BooleanSupplier condition, final int timeout, final int interval) {
        super(timeout, interval);
        this.condition = condition;
    }

    @Override
    public final boolean condition() {
        return condition.getAsBoolean();
    }

    public static boolean sleepUntil(final BooleanSupplier condition, final int timeout) {
        return new Sleep(condition, timeout).sleep();
    }

    public static boolean sleepUntil(final BooleanSupplier condition, final int minTimeout,
                                           final int maxTimeout) {
        return sleepUntil(condition, ThreadLocalRandom.current().nextInt(minTimeout, maxTimeout));
    }

//    public static boolean sleepUntil(final BooleanSupplier condition, final int timeout, final int interval) {
//        return new Sleep(condition, timeout, interval).sleep();
//    }
}