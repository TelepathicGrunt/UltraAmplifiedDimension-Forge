package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;


public class FossilUA extends Feature<NoFeatureConfig>
{
	public FossilUA(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private static final ResourceLocation STRUCTURE_SPINE_01 = new ResourceLocation("fossil/spine_1");
	private static final ResourceLocation STRUCTURE_SPINE_02 = new ResourceLocation("fossil/spine_2");
	private static final ResourceLocation STRUCTURE_SPINE_03 = new ResourceLocation("fossil/spine_3");
	private static final ResourceLocation STRUCTURE_SPINE_04 = new ResourceLocation("fossil/spine_4");
	private static final ResourceLocation STRUCTURE_SPINE_01_COAL = new ResourceLocation("fossil/spine_1_coal");
	private static final ResourceLocation STRUCTURE_SPINE_02_COAL = new ResourceLocation("fossil/spine_2_coal");
	private static final ResourceLocation STRUCTURE_SPINE_03_COAL = new ResourceLocation("fossil/spine_3_coal");
	private static final ResourceLocation STRUCTURE_SPINE_04_COAL = new ResourceLocation("fossil/spine_4_coal");
	private static final ResourceLocation STRUCTURE_SKULL_01 = new ResourceLocation("fossil/skull_1");
	private static final ResourceLocation STRUCTURE_SKULL_02 = new ResourceLocation("fossil/skull_2");
	private static final ResourceLocation STRUCTURE_SKULL_03 = new ResourceLocation("fossil/skull_3");
	private static final ResourceLocation STRUCTURE_SKULL_04 = new ResourceLocation("fossil/skull_4");
	private static final ResourceLocation STRUCTURE_SKULL_01_COAL = new ResourceLocation("fossil/skull_1_coal");
	private static final ResourceLocation STRUCTURE_SKULL_02_COAL = new ResourceLocation("fossil/skull_2_coal");
	private static final ResourceLocation STRUCTURE_SKULL_03_COAL = new ResourceLocation("fossil/skull_3_coal");
	private static final ResourceLocation STRUCTURE_SKULL_04_COAL = new ResourceLocation("fossil/skull_4_coal");
	private static final ResourceLocation[] FOSSILS = new ResourceLocation[] { STRUCTURE_SPINE_01, STRUCTURE_SPINE_02, STRUCTURE_SPINE_03, STRUCTURE_SPINE_04, STRUCTURE_SKULL_01, STRUCTURE_SKULL_02, STRUCTURE_SKULL_03, STRUCTURE_SKULL_04 };
	private static final ResourceLocation[] FOSSILS_COAL = new ResourceLocation[] { STRUCTURE_SPINE_01_COAL, STRUCTURE_SPINE_02_COAL, STRUCTURE_SPINE_03_COAL, STRUCTURE_SPINE_04_COAL, STRUCTURE_SKULL_01_COAL, STRUCTURE_SKULL_02_COAL, STRUCTURE_SKULL_03_COAL, STRUCTURE_SKULL_04_COAL };


	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random rand, BlockPos position, NoFeatureConfig config)
	{

		if (!ConfigUA.miniStructureGeneration)
		{
			return false;
		}

		Random random = world.getRandom();
		Rotation[] arotation = Rotation.values();
		Rotation rotation = arotation[random.nextInt(arotation.length)];
		int i = random.nextInt(FOSSILS.length);
		TemplateManager templatemanager = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager();
		Template template = templatemanager.getTemplateDefaulted(FOSSILS[i]);
		Template template1 = templatemanager.getTemplateDefaulted(FOSSILS_COAL[i]);
		ChunkPos chunkpos = new ChunkPos(position);
		MutableBoundingBox mutableboundingbox = new MutableBoundingBox(chunkpos.getXStart(), 0, chunkpos.getZStart(), chunkpos.getXEnd(), 256, chunkpos.getZEnd());
		PlacementSettings placementsettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(mutableboundingbox).setRandom(random);

		BlockPos blockpos1 = template.getZeroPositionWithTransform(position, Mirror.NONE, rotation);
		IntegrityProcessor integrityprocessor = new IntegrityProcessor(0.9F);
		placementsettings.clearProcessors().addProcessor(integrityprocessor);
		template.addBlocksToWorld(world, blockpos1, placementsettings, 4);
		placementsettings.func_215220_b(integrityprocessor);
		IntegrityProcessor integrityprocessor1 = new IntegrityProcessor(0.1F);
		placementsettings.clearProcessors().addProcessor(integrityprocessor1);
		template1.addBlocksToWorld(world, blockpos1, placementsettings, 4);
		return true;
	}
}