package com.telepathicgrunt.ultraamplifieddimension.generation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.mixin.ChunkGeneratorAccessor;
import com.telepathicgrunt.ultraamplifieddimension.mixin.DimensionSettingsInvoker;
import com.telepathicgrunt.ultraamplifieddimension.mixin.NoiseChunkGeneratorAccessor;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.gen.settings.ScalingSettings;
import net.minecraft.world.gen.settings.SlideSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;


public class UADChunkGenerator extends NoiseChunkGenerator {

    private static final float[] BIOME_WEIGHTING_KERNEL = Util.make(new float[25], (floats) -> {
        for (int xRelative = -2; xRelative <= 2; ++xRelative) {
            for (int zRelative = -2; zRelative <= 2; ++zRelative) {
                float biomeWeighting = 10.0F / MathHelper.sqrt(xRelative * xRelative + zRelative * zRelative + 0.2F);
                floats[xRelative + 2 + (zRelative + 2) * 5] = biomeWeighting;
            }
        }
    });

    private static final float[] NOISE_KERNAL = Util.make(new float[13824], (p_236094_0_) -> {
        for(int i = 0; i < 24; ++i) {
            for(int j = 0; j < 24; ++j) {
                for(int k = 0; k < 24; ++k) {
                    p_236094_0_[i * 24 * 24 + j * 24 + k] = (float)func_222554_b(j - 12, k - 12, i - 12);
                }
            }
        }
    });

    private static final Codec<Double> RANGE_CODEC = Codec.doubleRange(Double.MIN_VALUE, Double.MAX_VALUE);

    public static final Codec<ScalingSettings> UAD_SCALING_CODEC = RecordCodecBuilder.create((scalingSettingsInstance) ->
            scalingSettingsInstance.group(
                    RANGE_CODEC.fieldOf("xz_scale").forGetter(ScalingSettings::func_236151_a_),
                    RANGE_CODEC.fieldOf("y_scale").forGetter(ScalingSettings::func_236153_b_),
                    RANGE_CODEC.fieldOf("xz_factor").forGetter(ScalingSettings::func_236154_c_),
                    RANGE_CODEC.fieldOf("y_factor").forGetter(ScalingSettings::func_236155_d_))
                        .apply(scalingSettingsInstance, ScalingSettings::new));

    public static final Codec<NoiseSettings> UAD_NOISE_SETTINGS_CODEC = RecordCodecBuilder.create((noiseSettingsInstance) ->
            noiseSettingsInstance.group(Codec.intRange(0, 256).fieldOf("height")
                    .forGetter(NoiseSettings::func_236169_a_), UAD_SCALING_CODEC.fieldOf("sampling")
                    .forGetter(NoiseSettings::func_236171_b_), SlideSettings.field_236182_a_.fieldOf("top_slide")
                    .forGetter(NoiseSettings::func_236172_c_), SlideSettings.field_236182_a_.fieldOf("bottom_slide")
                    .forGetter(NoiseSettings::func_236173_d_), Codec.intRange(Integer.MIN_VALUE, Integer.MAX_VALUE).fieldOf("size_horizontal")
                    .forGetter(NoiseSettings::func_236174_e_), Codec.intRange(Integer.MIN_VALUE, Integer.MAX_VALUE).fieldOf("size_vertical")
                    .forGetter(NoiseSettings::func_236175_f_), Codec.DOUBLE.fieldOf("density_factor")
                    .forGetter(NoiseSettings::func_236176_g_), Codec.DOUBLE.fieldOf("density_offset")
                    .forGetter(NoiseSettings::func_236177_h_), Codec.BOOL.fieldOf("simplex_surface_noise")
                    .forGetter(NoiseSettings::func_236178_i_), Codec.BOOL.optionalFieldOf("random_density_offset", Boolean.FALSE, Lifecycle.experimental())
                    .forGetter(NoiseSettings::func_236179_j_), Codec.BOOL.optionalFieldOf("island_noise_override", Boolean.FALSE, Lifecycle.experimental())
                    .forGetter(NoiseSettings::func_236180_k_), Codec.BOOL.optionalFieldOf("amplified", Boolean.FALSE, Lifecycle.experimental()).forGetter(NoiseSettings::func_236181_l_))
                        .apply(noiseSettingsInstance, NoiseSettings::new));

    public static final Codec<DimensionSettings> UAD_DIMENSION_SETTINGS_CODEC = RecordCodecBuilder.create((dimensionSettingsInstance) ->
            dimensionSettingsInstance.group(DimensionStructuresSettings.field_236190_a_.fieldOf("structures")
                    .forGetter(DimensionSettings::getStructures), UAD_NOISE_SETTINGS_CODEC.fieldOf("noise")
                    .forGetter(DimensionSettings::getNoise), BlockState.CODEC.fieldOf("default_block")
                    .forGetter(DimensionSettings::getDefaultBlock), BlockState.CODEC.fieldOf("default_fluid")
                    .forGetter(DimensionSettings::getDefaultFluid), Codec.intRange(-20, 276).fieldOf("bedrock_roof_position")
                    .forGetter(DimensionSettings::func_236117_e_), Codec.intRange(-20, 276).fieldOf("bedrock_floor_position")
                    .forGetter(DimensionSettings::func_236118_f_), Codec.intRange(0, 255).fieldOf("sea_level")
                    .forGetter(DimensionSettings::func_236119_g_), Codec.BOOL.fieldOf("disable_mob_generation")
                    .forGetter(dimensionSettings -> ((DimensionSettingsInvoker)(Object)dimensionSettings).invokefunc_236120_h_()))
                        .apply(dimensionSettingsInstance, DimensionSettingsInvoker::invokeinit));


    public static final Codec<NoiseChunkGenerator> UAD_CHUNK_GENERATOR_CODEC = RecordCodecBuilder.create((noiseChunkGeneratorInstance) -> noiseChunkGeneratorInstance.group(
                    BiomeProvider.CODEC.fieldOf("biome_source").forGetter((noiseChunkGenerator) -> ((ChunkGeneratorAccessor)noiseChunkGenerator).getbiomeProvider()),
                    Codec.LONG.fieldOf("seed").stable().forGetter((noiseChunkGenerator) -> ((NoiseChunkGeneratorAccessor)noiseChunkGenerator).getfield_236084_w_()),
                    UAD_DIMENSION_SETTINGS_CODEC.fieldOf("settings").forGetter((noiseChunkGenerator) -> ((NoiseChunkGeneratorAccessor)noiseChunkGenerator).getfield_236080_h_().get()))
                        .apply(noiseChunkGeneratorInstance, noiseChunkGeneratorInstance.stable(UADChunkGenerator::new)));

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return UAD_CHUNK_GENERATOR_CODEC;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ChunkGenerator func_230349_a_(long seed) {
        return new UADChunkGenerator(this.biomeProvider.getBiomeProvider(seed), seed, this.field_236080_h_.get());
    }

    public UADChunkGenerator(BiomeProvider biomeProvider, long seed, DimensionSettings dimensionSettings) {
        super(biomeProvider, seed, () -> dimensionSettings);
    }


    private void fillNoiseColumn(double[] noiseColumn, int noiseX, int noiseZ) {
        NoiseSettings noisesettings = this.field_236080_h_.get().getNoise();
        double d0;
        double d1;
        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;

        for(int k = -2; k <= 2; ++k) {
            for(int l = -2; l <= 2; ++l) {
               // Biome biome = this.biomeProvider.getNoiseBiome(noiseX + k, j, noiseZ + l);
                float depthWeight = 0; // biome.getDepth();
                float scaleWeight = 0; // biome.getScale();

                // Does not take into account the biome's base height and scale.
                // Making the terrain's height based on biomes took away some of the magic and coolness.
                // Thus, all biomes now have a uniformed base and scale applied to their terrain.
                // offset + scale
                depthWeight = 1.0F + (0.0F + 0.4F) * 2F;
                scaleWeight = 1.0F + (0.0F + 0.3F) * 12F;

                float f9 = BIOME_WEIGHTING_KERNEL[k + 2 + (l + 2) * 5] / (depthWeight + 2.0F);
                f += scaleWeight * f9;
                f1 += depthWeight * f9;
                f2 += f9;
            }
        }

        float f10 = f1 / f2;
        float f11 = f / f2;
        double d16 = f10 * 0.5F - 0.125F;
        double d18 = f11 * 0.9F + 0.1F;
        d0 = d16 * 0.265625D;
        d1 = 96.0D / d18;

        double d12 = 684.412D * noisesettings.func_236171_b_().func_236151_a_();
        double d13 = 684.412D * noisesettings.func_236171_b_().func_236153_b_();
        double d14 = d12 / noisesettings.func_236171_b_().func_236154_c_();
        double d15 = d13 / noisesettings.func_236171_b_().func_236155_d_();
        double d17 = noisesettings.func_236172_c_().func_236186_a_();
        double d19 = noisesettings.func_236172_c_().func_236188_b_();
        double d20 = noisesettings.func_236172_c_().func_236189_c_();
        double d21 = noisesettings.func_236173_d_().func_236186_a_();
        double d2 = noisesettings.func_236173_d_().func_236188_b_();
        double d3 = noisesettings.func_236173_d_().func_236189_c_();
        double d4 = noisesettings.func_236179_j_() ? this.func_236095_c_(noiseX, noiseZ) : 0.0D;
        double d5 = noisesettings.func_236176_g_();
        double d6 = noisesettings.func_236177_h_();

        for(int i1 = 0; i1 <= ((NoiseChunkGeneratorAccessor)this).getNoiseSizeY(); ++i1) {
            double d7 = this.func_222552_a(noiseX, i1, noiseZ, d12, d13, d14, d15);
            double d8 = 1.0D - (double)i1 * 2.0D / (double)((NoiseChunkGeneratorAccessor)this).getNoiseSizeY() + d4;
            double d9 = d8 * d5 + d6;
            double d10 = (d9 + d0) * d1;
            if (d10 > 0.0D) {
                d7 = d7 + d10 * 4.0D;
            } else {
                d7 = d7 + d10;
            }

            if (d19 > 0.0D) {
                double d11 = ((double)(((NoiseChunkGeneratorAccessor)this).getNoiseSizeY() - i1) - d20) / d19;
                d7 = MathHelper.clampedLerp(d17, d7, d11);
            }

            if (d2 > 0.0D) {
                double d22 = ((double)i1 - d3) / d2;
                d7 = MathHelper.clampedLerp(d21, d7, d22);
            }

            noiseColumn[i1] = d7;
        }
    }


    /**
     * SuperCoder's optimization she PRed into Lithium.
     * https://github.com/jellysquid3/lithium-fabric/blob/96c4347f2feeeb7310906760566ea2f1ed02e2cd/src/main/java/me/jellysquid/mods/lithium/mixin/gen/fast_noise_interpolation/NoiseChunkGeneratorMixin.java
     *
     * I asked if I could recreate it here and was given the green light.
     * Special thanks to SuperCoder!
     *
     * Note from Lithium:
     *  To generate it's terrain, Minecraft uses two different perlin noises.
     *  It interpolates these two noises to create the final sample at a position.
     *  However, the interpolation noise is not all that good and spends most of it's time at > 1 or < 0, rendering
     *  one of the noises completely unnecessary in the process.
     *  By taking advantage of that, we can reduce the sampling needed per block through the interpolation noise.
     */
    private double func_222552_a(int x, int y, int z, double horizontalScale, double verticalScale, double horizontalStretch, double verticalStretch) {
        // This controls both the frequency and amplitude of the noise.
        double frequency = 1.0;
        double interpolationValue = 0.0;

        // Calculate interpolation data to decide what noise to sample.
        for (int octave = 0; octave < 8; octave++) {
            double scaledVerticalScale = verticalStretch * frequency;
            double scaledY = y * scaledVerticalScale;

            interpolationValue += sampleOctave(((NoiseChunkGeneratorAccessor)this).getField_222570_q().getOctave(octave),
                    OctavesNoiseGenerator.maintainPrecision(x * horizontalStretch * frequency),
                    OctavesNoiseGenerator.maintainPrecision(scaledY),
                    OctavesNoiseGenerator.maintainPrecision(z * horizontalStretch * frequency), scaledVerticalScale, scaledY, frequency);

            frequency /= 2.0;
        }

        double clampedInterpolation = (interpolationValue / 10.0 + 1.0) / 2.0;

        if (clampedInterpolation >= 1) {
            // Sample only upper noise, as the lower noise will be interpolated out.
            frequency = 1.0;
            double noise = 0.0;
            for (int octave = 0; octave < 16; octave++) {
                double scaledVerticalScale = verticalScale * frequency;
                double scaledY = y * scaledVerticalScale;

                noise += sampleOctave(((NoiseChunkGeneratorAccessor)this).getField_222569_p().getOctave(octave),
                        OctavesNoiseGenerator.maintainPrecision(x * horizontalScale * frequency),
                        OctavesNoiseGenerator.maintainPrecision(scaledY),
                        OctavesNoiseGenerator.maintainPrecision(z * horizontalScale * frequency), scaledVerticalScale, scaledY, frequency);

                frequency /= 2.0;
            }

            return noise / 512.0;
        }
        else if (clampedInterpolation <= 0) {
            // Sample only lower noise, as the upper noise will be interpolated out.
            frequency = 1.0;
            double noise = 0.0;
            for (int octave = 0; octave < 16; octave++) {
                double scaledVerticalScale = verticalScale * frequency;
                double scaledY = y * scaledVerticalScale;
                noise += sampleOctave(((NoiseChunkGeneratorAccessor)this).getField_222568_o().getOctave(octave),
                        OctavesNoiseGenerator.maintainPrecision(x * horizontalScale * frequency),
                        OctavesNoiseGenerator.maintainPrecision(scaledY),
                        OctavesNoiseGenerator.maintainPrecision(z * horizontalScale * frequency), scaledVerticalScale, scaledY, frequency);

                frequency /= 2.0;
            }

            return noise / 512.0;
        }
        else {
            // [VanillaCopy] SurfaceChunkGenerator#sampleNoise
            // Sample both and interpolate, as in vanilla.

            frequency = 1.0;
            double lowerNoise = 0.0;
            double upperNoise = 0.0;

            for (int octave = 0; octave < 16; octave++) {
                // Pre calculate these values to share them
                double scaledVerticalScale = verticalScale * frequency;
                double scaledY = y * scaledVerticalScale;
                double xVal = OctavesNoiseGenerator.maintainPrecision(x * horizontalScale * frequency);
                double yVal = OctavesNoiseGenerator.maintainPrecision(scaledY);
                double zVal = OctavesNoiseGenerator.maintainPrecision(z * horizontalScale * frequency);

                upperNoise += sampleOctave(((NoiseChunkGeneratorAccessor)this).getField_222569_p().getOctave(octave), xVal, yVal, zVal, scaledVerticalScale, scaledY, frequency);
                lowerNoise += sampleOctave(((NoiseChunkGeneratorAccessor)this).getField_222568_o().getOctave(octave), xVal, yVal, zVal, scaledVerticalScale, scaledY, frequency);

                frequency /= 2.0;
            }

            // Vanilla behavior, return interpolated noise
            return MathHelper.lerp(clampedInterpolation, lowerNoise / 512.0, upperNoise / 512.0);
        }
    }

    /**
     * Also from SuperCoder and Lithium
     */
    private static double sampleOctave(ImprovedNoiseGenerator sampler, double x, double y, double z, double scaledVerticalScale, double scaledY, double frequency) {
        return sampler.func_215456_a(x, y, z, scaledVerticalScale, scaledY) / frequency;
    }


    private double[] func_222547_b(int p_222547_1_, int p_222547_2_) {
        double[] adouble = new double[((NoiseChunkGeneratorAccessor)this).getNoiseSizeY() + 1];
        this.fillNoiseColumn(adouble, p_222547_1_, p_222547_2_);
        return adouble;
    }

    private double func_236095_c_(int x, int z) {
        double d0 = ((NoiseChunkGeneratorAccessor)this).getField_236082_u_().getValue(
                x * 200,
                10.0D,
                z * 200,
                1.0D,
                0.0D,
                true);

        if (d0 < 0.0D) {
            d0 *= 3.0D;
        }

        double d2 = d0 * 24.575625D - 2.0D;
        return d2 < 0.0D ? d2 * 0.009486607142857142D : Math.min(d2, 1.0D) * 0.006640625D;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return this.func_236087_a_(x, z, null, heightmapType.getHeightLimitPredicate());
    }

    @Override
    public IBlockReader func_230348_a_(int p_230348_1_, int p_230348_2_) {
        BlockState[] ablockstate = new BlockState[((NoiseChunkGeneratorAccessor)this).getNoiseSizeY() * ((NoiseChunkGeneratorAccessor)this).getVerticalNoiseGranularity()];
        this.func_236087_a_(p_230348_1_, p_230348_2_, ablockstate, null);
        return new Blockreader(ablockstate);
    }

    private int func_236087_a_(int p_236087_1_, int p_236087_2_, @Nullable BlockState[] p_236087_3_, @Nullable Predicate<BlockState> p_236087_4_) {
        int i = Math.floorDiv(p_236087_1_, ((NoiseChunkGeneratorAccessor)this).getHorizontalNoiseGranularity());
        int j = Math.floorDiv(p_236087_2_, ((NoiseChunkGeneratorAccessor)this).getHorizontalNoiseGranularity());
        int k = Math.floorMod(p_236087_1_, ((NoiseChunkGeneratorAccessor)this).getHorizontalNoiseGranularity());
        int l = Math.floorMod(p_236087_2_, ((NoiseChunkGeneratorAccessor)this).getHorizontalNoiseGranularity());
        double d0 = (double)k / (double)((NoiseChunkGeneratorAccessor)this).getHorizontalNoiseGranularity();
        double d1 = (double)l / (double)((NoiseChunkGeneratorAccessor)this).getHorizontalNoiseGranularity();
        double[][] adouble = new double[][]{this.func_222547_b(i, j), this.func_222547_b(i, j + 1), this.func_222547_b(i + 1, j), this.func_222547_b(i + 1, j + 1)};

        for(int i1 = ((NoiseChunkGeneratorAccessor)this).getNoiseSizeY() - 1; i1 >= 0; --i1) {
            double d2 = adouble[0][i1];
            double d3 = adouble[1][i1];
            double d4 = adouble[2][i1];
            double d5 = adouble[3][i1];
            double d6 = adouble[0][i1 + 1];
            double d7 = adouble[1][i1 + 1];
            double d8 = adouble[2][i1 + 1];
            double d9 = adouble[3][i1 + 1];

            for(int j1 = ((NoiseChunkGeneratorAccessor)this).getVerticalNoiseGranularity() - 1; j1 >= 0; --j1) {
                double d10 = (double)j1 / (double)((NoiseChunkGeneratorAccessor)this).getVerticalNoiseGranularity();
                double d11 = MathHelper.lerp3(d10, d0, d1, d2, d6, d4, d8, d3, d7, d5, d9);
                int k1 = i1 * ((NoiseChunkGeneratorAccessor)this).getVerticalNoiseGranularity() + j1;
                BlockState blockstate = this.func_236086_a_(d11, k1);
                if (p_236087_3_ != null) {
                    p_236087_3_[k1] = blockstate;
                }

                if (p_236087_4_ != null && p_236087_4_.test(blockstate)) {
                    return k1 + 1;
                }
            }
        }

        return 0;
    }

    @Override
    protected BlockState func_236086_a_(double p_236086_1_, int p_236086_3_) {
        BlockState blockstate;
        if (p_236086_1_ > 0.0D) {
            blockstate = this.defaultBlock;
        } else if (p_236086_3_ < this.getSeaLevel()) {
            blockstate = this.defaultFluid;
        } else {
            blockstate = Blocks.AIR.getDefaultState();
        }

        return blockstate;
    }
    
    @Override
    public void func_230352_b_(IWorld p_230352_1_, StructureManager p_230352_2_, IChunk p_230352_3_) {
        ObjectList<StructurePiece> objectlist = new ObjectArrayList<>(10);
        ObjectList<JigsawJunction> objectlist1 = new ObjectArrayList<>(32);
        ChunkPos chunkpos = p_230352_3_.getPos();
        int i = chunkpos.x;
        int j = chunkpos.z;
        int k = i << 4;
        int l = j << 4;

        for(Structure<?> structure : Structure.field_236384_t_) {
            p_230352_2_.func_235011_a_(SectionPos.from(chunkpos, 0), structure).forEach((p_236089_5_) -> {
                for(StructurePiece structurepiece1 : p_236089_5_.getComponents()) {
                    if (structurepiece1.func_214810_a(chunkpos, 12)) {
                        if (structurepiece1 instanceof AbstractVillagePiece) {
                            AbstractVillagePiece abstractvillagepiece = (AbstractVillagePiece)structurepiece1;
                            JigsawPattern.PlacementBehaviour jigsawpattern$placementbehaviour = abstractvillagepiece.getJigsawPiece().getPlacementBehaviour();
                            if (jigsawpattern$placementbehaviour == JigsawPattern.PlacementBehaviour.RIGID) {
                                objectlist.add(abstractvillagepiece);
                            }

                            for(JigsawJunction jigsawjunction1 : abstractvillagepiece.getJunctions()) {
                                int l5 = jigsawjunction1.getSourceX();
                                int i6 = jigsawjunction1.getSourceZ();
                                if (l5 > k - 12 && i6 > l - 12 && l5 < k + 15 + 12 && i6 < l + 15 + 12) {
                                    objectlist1.add(jigsawjunction1);
                                }
                            }
                        } else {
                            objectlist.add(structurepiece1);
                        }
                    }
                }

            });
        }

        double[][][] adouble = new double[2][((NoiseChunkGeneratorAccessor)this).getNoiseSizeZ() + 1][((NoiseChunkGeneratorAccessor)this).getNoiseSizeY() + 1];

        for(int i5 = 0; i5 < ((NoiseChunkGeneratorAccessor)this).getNoiseSizeZ() + 1; ++i5) {
            adouble[0][i5] = new double[((NoiseChunkGeneratorAccessor)this).getNoiseSizeY() + 1];
            this.fillNoiseColumn(adouble[0][i5], i * ((NoiseChunkGeneratorAccessor)this).getNoiseSizeX(), j * ((NoiseChunkGeneratorAccessor)this).getNoiseSizeZ() + i5);
            adouble[1][i5] = new double[((NoiseChunkGeneratorAccessor)this).getNoiseSizeY() + 1];
        }

        ChunkPrimer chunkprimer = (ChunkPrimer)p_230352_3_;
        Heightmap heightmap = chunkprimer.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap heightmap1 = chunkprimer.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        ObjectListIterator<StructurePiece> objectlistiterator = objectlist.iterator();
        ObjectListIterator<JigsawJunction> objectlistiterator1 = objectlist1.iterator();

        for(int i1 = 0; i1 < ((NoiseChunkGeneratorAccessor)this).getNoiseSizeX(); ++i1) {
            for(int j1 = 0; j1 < ((NoiseChunkGeneratorAccessor)this).getNoiseSizeZ() + 1; ++j1) {
                this.fillNoiseColumn(adouble[1][j1], i * ((NoiseChunkGeneratorAccessor)this).getNoiseSizeX() + i1 + 1, j * ((NoiseChunkGeneratorAccessor)this).getNoiseSizeZ() + j1);
            }

            for(int j5 = 0; j5 < ((NoiseChunkGeneratorAccessor)this).getNoiseSizeZ(); ++j5) {
                ChunkSection chunksection = chunkprimer.getSection(15);
                chunksection.lock();

                for(int k1 = ((NoiseChunkGeneratorAccessor)this).getNoiseSizeY() - 1; k1 >= 0; --k1) {
                    double d0 = adouble[0][j5][k1];
                    double d1 = adouble[0][j5 + 1][k1];
                    double d2 = adouble[1][j5][k1];
                    double d3 = adouble[1][j5 + 1][k1];
                    double d4 = adouble[0][j5][k1 + 1];
                    double d5 = adouble[0][j5 + 1][k1 + 1];
                    double d6 = adouble[1][j5][k1 + 1];
                    double d7 = adouble[1][j5 + 1][k1 + 1];

                    for(int l1 = ((NoiseChunkGeneratorAccessor)this).getVerticalNoiseGranularity() - 1; l1 >= 0; --l1) {
                        int i2 = k1 * ((NoiseChunkGeneratorAccessor)this).getVerticalNoiseGranularity() + l1;
                        int j2 = i2 & 15;
                        int k2 = i2 >> 4;
                        if (chunksection.getYLocation() >> 4 != k2) {
                            chunksection.unlock();
                            chunksection = chunkprimer.getSection(k2);
                            chunksection.lock();
                        }

                        double d8 = (double)l1 / (double)((NoiseChunkGeneratorAccessor)this).getVerticalNoiseGranularity();
                        double d9 = MathHelper.lerp(d8, d0, d4);
                        double d10 = MathHelper.lerp(d8, d2, d6);
                        double d11 = MathHelper.lerp(d8, d1, d5);
                        double d12 = MathHelper.lerp(d8, d3, d7);

                        for(int l2 = 0; l2 < ((NoiseChunkGeneratorAccessor)this).getHorizontalNoiseGranularity(); ++l2) {
                            int i3 = k + i1 * ((NoiseChunkGeneratorAccessor)this).getHorizontalNoiseGranularity() + l2;
                            int j3 = i3 & 15;
                            double d13 = (double)l2 / (double)((NoiseChunkGeneratorAccessor)this).getHorizontalNoiseGranularity();
                            double d14 = MathHelper.lerp(d13, d9, d10);
                            double d15 = MathHelper.lerp(d13, d11, d12);

                            for(int k3 = 0; k3 < ((NoiseChunkGeneratorAccessor)this).getHorizontalNoiseGranularity(); ++k3) {
                                int l3 = l + j5 * ((NoiseChunkGeneratorAccessor)this).getHorizontalNoiseGranularity() + k3;
                                int i4 = l3 & 15;
                                double d16 = (double)k3 / (double)((NoiseChunkGeneratorAccessor)this).getHorizontalNoiseGranularity();
                                double d17 = MathHelper.lerp(d16, d14, d15);
                                double d18 = MathHelper.clamp(d17 / 200.0D, -1.0D, 1.0D);

                                int jigsawY;
                                int jigsawZ;
                                int l4;
                                for(d18 = d18 / 2.0D - d18 * d18 * d18 / 24.0D; objectlistiterator.hasNext(); d18 += func_222556_a(jigsawY, jigsawZ, l4) * 0.8D) {
                                    StructurePiece structurepiece = objectlistiterator.next();
                                    MutableBoundingBox mutableboundingbox = structurepiece.getBoundingBox();
                                    jigsawY = Math.max(0, Math.max(mutableboundingbox.minX - i3, i3 - mutableboundingbox.maxX));
                                    jigsawZ = i2 - (mutableboundingbox.minY + (structurepiece instanceof AbstractVillagePiece ? ((AbstractVillagePiece)structurepiece).getGroundLevelDelta() : 0));
                                    l4 = Math.max(0, Math.max(mutableboundingbox.minZ - l3, l3 - mutableboundingbox.maxZ));
                                }

                                objectlistiterator.back(objectlist.size());

                                while(objectlistiterator1.hasNext()) {
                                    JigsawJunction jigsawjunction = objectlistiterator1.next();
                                    int jigsawX = i3 - jigsawjunction.getSourceX();
                                    jigsawY = i2 - jigsawjunction.getSourceGroundY();
                                    jigsawZ = l3 - jigsawjunction.getSourceZ();
                                    d18 += func_222556_a(jigsawX, jigsawY, jigsawZ) * 0.4D;
                                }

                                objectlistiterator1.back(objectlist1.size());
                                BlockState blockstate = this.func_236086_a_(d18, i2);
                                if (blockstate != Blocks.AIR.getDefaultState()) {
                                    blockpos$mutable.setPos(i3, i2, l3);
                                    if (blockstate.getLightValue(chunkprimer, blockpos$mutable) != 0) {
                                        chunkprimer.addLightPosition(blockpos$mutable);
                                    }

                                    chunksection.setBlockState(j3, j2, i4, blockstate, false);
                                    heightmap.update(j3, i2, i4, blockstate);
                                    heightmap1.update(j3, i2, i4, blockstate);
                                }
                            }
                        }
                    }
                }

                chunksection.unlock();
            }

            double[][] adouble1 = adouble[0];
            adouble[0] = adouble[1];
            adouble[1] = adouble1;
        }

    }

    private static double func_222556_a(int x, int y, int z) {
        int xPos = x + 12;
        int yPos = y + 12;
        int zPos = z + 12;
        if (xPos >= 0 && xPos < 24) {
            if (yPos >= 0 && yPos < 24) {
                return zPos >= 0 && zPos < 24 ? (double)NOISE_KERNAL[zPos * 24 * 24 + xPos * 24 + yPos] : 0.0D;
            } else {
                return 0.0D;
            }
        } else {
            return 0.0D;
        }
    }

    private static double func_222554_b(int x, int y, int z) {
        double d0 = (x * x) + (z * z);
        double d1 = (double)y + 0.5D;
        double d2 = d1 * d1;
        double d3 = Math.pow(Math.E, -(d2 / 16.0D + d0 / 16.0D));
        double d4 = -d1 * MathHelper.fastInvSqrt(d2 / 2.0D + d0 / 2.0D) / 2.0D;
        return d4 * d3;
    }
}