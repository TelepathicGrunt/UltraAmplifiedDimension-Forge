package net.TelepathicGrunt.UltraAmplified.World.dimension;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/*
 * Code borrowed from McJty's LostCity mod
 * Source: https://github.com/McJtyMods/LostCities/blob/1.12/src/main/java/mcjty/lostcities/varia/CustomTeleporter.java
 */
public class UATeleporter extends Teleporter {

	public UATeleporter(WorldServer world, double x, double y, double z) {
        super(world);
        this.worldServer = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private final WorldServer worldServer;
    private double x, y, z;

    @Override
    public void placeInPortal(@Nonnull Entity entity, float rotationYaw) {
        this.worldServer.getBlockState(new BlockPos((int) this.x, (int) this.y, (int) this.z));

        entity.setPosition(this.x, this.y, this.z);
        entity.motionX = 0.0f;
        entity.motionY = 0.0f;
        entity.motionZ = 0.0f;
    }

    public static void teleportToDimension(EntityPlayer player, int dimension, BlockPos pos){
        teleportToDimension(player, dimension, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
    }

    public static void teleportToDimension(EntityPlayer player, int dimension, double x, double y, double z) {
        int oldDimension = player.getEntityWorld().provider.getDimension();
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
        MinecraftServer server = ((EntityPlayerMP) player).getEntityWorld().getMinecraftServer();
        WorldServer worldServer = server.getWorld(dimension);
        loadSurroundingArea(player, worldServer);
        player.addExperienceLevel(0);

        if (worldServer == null || worldServer.getMinecraftServer() == null){ //Dimension doesn't exist
            throw new IllegalArgumentException("Dimension: "+dimension+" doesn't exist!");
        }

        worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new UATeleporter(worldServer, x, y, z));
        player.setPositionAndUpdate(x, y, z);
        if (oldDimension == 1) {
            // For some reason teleporting out of the end does weird things.
            player.setPositionAndUpdate(x, y, z);
            worldServer.spawnEntity(player);
            worldServer.updateEntityWithOptionalForce(player, false);
        }
    }
    

    //need to load chunks first before teleporting 
	private static void loadSurroundingArea(Entity entity, World worldIn) {

		int x = MathHelper.floor(entity.posX) >> 4;
		int z = MathHelper.floor(entity.posZ) >> 4;

		for (int dx = -2; dx <= 2; dx++) {
			for (int dz = -2; dz <= 2; dz++) {
				worldIn.getChunkFromBlockCoords(new BlockPos(x + dx, 60, z + dz));
			}
		}
	}
}