package net.kullmar.bots.agility;

public class AgilitySession {
    private long startTime;
    private int marksCount;
    private int xpGained;

    public int getXpPerHour() {
        return Math.round(xpGained / getTimePassedInHours());
    }

    private float getTimePassedInHours() {
        return (System.currentTimeMillis() - startTime)/1000f/3600f;
    }
}
