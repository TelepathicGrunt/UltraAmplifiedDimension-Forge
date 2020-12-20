package com.telepathicgrunt.ultraamplifieddimension.structures;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.Level;

public class GenericJigsawStructure extends AbstractBaseStructure {
    private final ResourceLocation START_POOL;
    private final int STRUCTURE_SIZE;
    private final int PIECE_Y_OFFSET;
    private final int BOUNDS_Y_OFFSET;
    private final int BIOME_RANGE;

    public GenericJigsawStructure(Codec<NoFeatureConfig> config, ResourceLocation poolRL, int structureSize, int pieceYOffset, int boundsYOffset, int biomeRange) {
        super(config);
        START_POOL = poolRL;
        STRUCTURE_SIZE = structureSize;
        PIECE_Y_OFFSET = pieceYOffset;
        BOUNDS_Y_OFFSET = boundsYOffset;
        BIOME_RANGE = biomeRange;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig NoFeatureConfig) {
         for (int curChunkX = chunkX - BIOME_RANGE; curChunkX <= chunkX + BIOME_RANGE; curChunkX++) {
            for (int curChunkZ = chunkZ - BIOME_RANGE; curChunkZ <= chunkZ + BIOME_RANGE; curChunkZ++) {
                if (curChunkX != chunkX &&
                    curChunkZ != chunkZ &&
                    !biomeSource.getNoiseBiome(curChunkX << 2, 60, curChunkZ << 2).getGenerationSettings().hasStructure(this))
                {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
        return MainStart::new;
    }

    public class MainStart extends MarginedStructureStart<NoFeatureConfig> {
        public MainStart(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager structureManager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig NoFeatureConfig) {

            BlockPos blockpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
            JigsawManager.func_242837_a(
                    dynamicRegistryManager,
                    new VillageConfig(() -> dynamicRegistryManager.getRegistry(Registry.JIGSAW_POOL_KEY)
                            .getOrDefault(START_POOL),
                            STRUCTURE_SIZE),
                    AbstractVillagePiece::new,
                    chunkGenerator,
                    structureManager,
                    blockpos,
                    this.components,
                    this.rand,
                    true,
                    true);

            // **THE FOLLOWING TWO LINES ARE OPTIONAL**
            //
            // Right here, you can do interesting stuff with the pieces in this.components such as offset the
            // center piece by 50 blocks up for no reason, remove repeats of a piece or add a new piece so
            // only 1 of that piece exists, etc. But you do not have access to the piece's blocks as this list
            // holds just the piece's size and positions. Blocks will be placed later in JigsawManager.
            //
            // In this case, we do `piece.offset` to raise pieces up by 1 block so that the house is not right on
            // the surface of water or sunken into land a bit.
            //
            // Then we extend the bounding box down by 1 by doing `piece.getBoundingBox().minY` which will cause the
            // land formed around the structure to be lowered and not cover the doorstep. You can raise the bounding
            // box to force the structure to be buried as well. This bounding box stuff with land is only for structures
            // that you added to Structure.field_236384_t_ field handles adding land around the base of structures.
            //
            // By lifting the house up by 1 and lowering the bounding box, the land at bottom of house will now be
            // flush with the surrounding terrain without blocking off the doorstep.
            this.components.forEach(piece -> piece.offset(0, PIECE_Y_OFFSET, 0));
            this.components.forEach(piece -> piece.getBoundingBox().minY += BOUNDS_Y_OFFSET);

            // Sets the bounds of the structure once you are finished.
            this.recalculateStructureSize();

            // I use to debug and quickly find out if the structure is spawning or not and where it is.
            // This is returning the coordinates of the center starting piece.
            UltraAmplifiedDimension.LOGGER.log(Level.DEBUG, "Rundown House at " +
                    this.components.get(0).getBoundingBox().minX + " " +
                    this.components.get(0).getBoundingBox().minY + " " +
                    this.components.get(0).getBoundingBox().minZ);
        }
    }
}