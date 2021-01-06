package com.telepathicgrunt.ultraamplifieddimension.utils;

public class WorldSeedHolder {
    private static long WORLD_SEED = 0;

    public static long getWorldSeed() {
        return WORLD_SEED;
    }

    public static void setWorldSeed(long worldSeed) {
        WORLD_SEED = worldSeed;
    }
}
