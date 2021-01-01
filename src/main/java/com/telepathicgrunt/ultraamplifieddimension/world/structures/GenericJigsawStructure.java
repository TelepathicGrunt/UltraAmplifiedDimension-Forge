package com.telepathicgrunt.ultraamplifieddimension.world.structures;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADConfiguredStructures;
import javafx.util.Pair;
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
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import org.apache.logging.log4j.Level;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenericJigsawStructure extends AbstractBaseStructure {
    protected final ResourceLocation START_POOL;
    protected final int STRUCTURE_SIZE;
    protected final int PIECE_Y_OFFSET;
    protected final int BOUNDS_Y_OFFSET;
    protected final int FIXED_HEIGHT;
    protected final boolean SPAWN_AT_TOP_LAND;
    protected final int BIOME_RANGE;
    protected Pair<ChunkGenerator, List<Map.Entry<Structure<?>, StructureSeparationSettings>>> STRUCTURE_SPACING_CACHE = null;

    public GenericJigsawStructure(Codec<NoFeatureConfig> config, ResourceLocation poolRL, int structureSize,
                                  int pieceYOffset, int boundsYOffset, int fixedHeight, int biomeRange) {
        super(config);
        START_POOL = poolRL;
        STRUCTURE_SIZE = structureSize;
        PIECE_Y_OFFSET = pieceYOffset;
        BOUNDS_Y_OFFSET = boundsYOffset;
        FIXED_HEIGHT = fixedHeight;
        SPAWN_AT_TOP_LAND = false;
        BIOME_RANGE = biomeRange;
    }

    public GenericJigsawStructure(Codec<NoFeatureConfig> config, ResourceLocation poolRL, int structureSize,
                                  int pieceYOffset, int boundsYOffset, int biomeRange) {
        super(config);
        START_POOL = poolRL;
        STRUCTURE_SIZE = structureSize;
        PIECE_Y_OFFSET = pieceYOffset;
        BOUNDS_Y_OFFSET = boundsYOffset;
        FIXED_HEIGHT = -1;
        SPAWN_AT_TOP_LAND = true;
        BIOME_RANGE = biomeRange;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig NoFeatureConfig) {
        for (int curChunkX = chunkX - BIOME_RANGE; curChunkX <= chunkX + BIOME_RANGE; curChunkX++) {
            for (int curChunkZ = chunkZ - BIOME_RANGE; curChunkZ <= chunkZ + BIOME_RANGE; curChunkZ++) {
                if (curChunkX != chunkX &&
                        curChunkZ != chunkZ &&
                        !biomeSource.getNoiseBiome(curChunkX << 2, 60, curChunkZ << 2).getGenerationSettings().hasStructure(this)) {
                    return false;
                }
            }
        }

        // Cache the filtered spacing as streaming and shit every structure spawn can get ridiculous.
        // We store the current chunkGenerator as if we go into different dimension, our cache is useless.
        // Gets all UAD structures to space away from (exclude self or else you can't do low spacing anymore)
        // Exclude very low spacing structures or else we can't spawn at all
        if(STRUCTURE_SPACING_CACHE == null || STRUCTURE_SPACING_CACHE.getKey() != chunkGenerator){
            STRUCTURE_SPACING_CACHE = new Pair<>(
                    chunkGenerator,
                    chunkGenerator.func_235957_b_().func_236195_a_().entrySet().stream()
                            .filter(entry -> entry.getKey() != this &&
                                            entry.getValue().func_236668_a_() > 4 &&
                                            UADConfiguredStructures.REGISTERED_UAD_STRUCTURES.containsKey(entry.getKey()))
                            .collect(Collectors.toList()));
        }

        int structureRange = 1;
        for (int curChunkX = chunkX - structureRange; curChunkX <= chunkX + structureRange; curChunkX++) {
            for (int curChunkZ = chunkZ - structureRange; curChunkZ <= chunkZ + structureRange; curChunkZ++) {
                for (Map.Entry<Structure<?>, StructureSeparationSettings> spacingSettings : STRUCTURE_SPACING_CACHE.getValue()) {
                    ChunkPos structurePos = spacingSettings.getKey().getChunkPosForStructure(
                            spacingSettings.getValue(),
                            seed,
                            chunkRandom,
                            chunkX,
                            chunkZ);

                    // The other structure is here! ABORT!
                    if (structurePos.x == curChunkX && structurePos.z == curChunkZ) {
                        return false;
                    }
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

            BlockPos blockpos = new BlockPos(chunkX * 16, FIXED_HEIGHT, chunkZ * 16);
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
                    false,
                    SPAWN_AT_TOP_LAND);

            // Calculate the size of the structure based on all all children.
            this.recalculateStructureSize();

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

            // I use to debug and quickly find out if the structure is spawning or not and where it is.
            // This is returning the coordinates of the center starting piece.
            UltraAmplifiedDimension.LOGGER.log(Level.WARN, this.getStructure().getStructureName() + " at " +
                    this.components.get(0).getBoundingBox().minX + " " +
                    this.components.get(0).getBoundingBox().minY + " " +
                    this.components.get(0).getBoundingBox().minZ);
        }
    }
}