package com.telepathicgrunt.ultraamplifieddimension.dimension;

import com.mojang.datafixers.util.Pair;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.blocks.AmplifiedPortalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraft.world.storage.WorldSavedData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UADWorldSavedData extends WorldSavedData {
// fabric version: https://hatebin.com/qtictvpncw

    public static final String DATA_KEY = UltraAmplifiedDimension.MODID + ":delayed_teleportation";
    private List<TeleportEntry> teleportingEntities = new ArrayList<>();
    private List<SpawnParticles> particles = new ArrayList<>();

    public UADWorldSavedData() {
        super(DATA_KEY);
    }

    public static UADWorldSavedData get(ServerWorld world) {
        return world.getSavedData().getOrCreate(UADWorldSavedData::new, DATA_KEY);
    }

    /**
     * Does *not* create worlds that don't already exist
     * So they should be created by the thing that schedules the tick, if possible
     * @param world The world that is being ticked and contains a data instance
     */
    public static void tick(ServerWorld world) {
        MinecraftServer server = world.getServer();
        UADWorldSavedData data = get(world);

        List<TeleportEntry> entityList = data.teleportingEntities;
        data.teleportingEntities = new ArrayList<>();

        List<SpawnParticles> particleList = data.particles;
        data.particles = new ArrayList<>();

        // Delay particle spawning by one tick after teleportation so the player can see their particles
        for (SpawnParticles entry : particleList) {
            ServerWorld targetWorld = server.getWorld(entry.targetWorld);
            if (targetWorld != null) {
                AmplifiedPortalBlock.createLotsOfParticles(targetWorld, entry.targetVec, targetWorld.rand);
            }
        }

        for (TeleportEntry entry : entityList) {
            ServerPlayerEntity player = server.getPlayerList().getPlayerByUUID(entry.playerUUID);
            ServerWorld targetWorld = server.getWorld(entry.targetWorld);
            if (player != null && targetWorld != null && player.world == world) {
                ChunkPos playerChunkPos = new ChunkPos(player.getPosition());
                targetWorld.getChunkProvider().registerTicket(TicketType.POST_TELEPORT, playerChunkPos, 1, player.getEntityId());

                player.fallDistance = 0;
                player.prevPosY = 0;
                player.teleport(
                        targetWorld,
                        entry.targetVec.getX(),
                        entry.targetVec.getY() + 0.2D,
                        entry.targetVec.getZ(),
                        entry.targetLook.getFirst(),
                        entry.targetLook.getSecond());


                // -0.5 on x and z to counter the builtin 0.5 offset in AmplifiedPortalBlock.createLotsOfParticles
                data.addParticle(entry.targetWorld, new Vector3d(entry.targetVec.x - 0.5d, entry.targetVec.y, entry.targetVec.z - 0.5d));
            }
        }
    }

    public void addPlayer(PlayerEntity player, RegistryKey<World> destination, Vector3d targetVec, Pair<Float, Float> targetLook) {
        this.teleportingEntities.add(new TeleportEntry(PlayerEntity.getUUID(player.getGameProfile()), destination, targetVec, targetLook));
    }

    public void addParticle(RegistryKey<World> destination, Vector3d targetVec) {
        this.particles.add(new SpawnParticles(destination, targetVec));
    }

    @Override
    public void read(CompoundNBT nbt) {
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return compound;
    }

    static class TeleportEntry {
        final UUID playerUUID;
        final RegistryKey<World> targetWorld;
        final Vector3d targetVec;
        final Pair<Float, Float> targetLook;

        public TeleportEntry(UUID playerUUID, RegistryKey<World> targetWorld, Vector3d targetVec, Pair<Float, Float> targetLook) {
            this.playerUUID = playerUUID;
            this.targetWorld = targetWorld;
            this.targetVec = targetVec;
            this.targetLook = targetLook;
        }
    }

    static class SpawnParticles {
        final RegistryKey<World> targetWorld;
        final Vector3d targetVec;

        public SpawnParticles(RegistryKey<World> targetWorld, Vector3d targetVec) {
            this.targetWorld = targetWorld;
            this.targetVec = targetVec;
        }
    }
}