package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class StrongholdStructureUA extends Structure<NoFeatureConfig>
{
    /**
     * --------------------------------------------------------------------------
     * |									|
     * |	HELLO READERS! IF YOU'RE HERE, YOU'RE PROBABLY			|
     * |	LOOKING FOR A TUTORIAL ON HOW TO DO STRUCTURES			|
     * |									|
     * -------------------------------------------------------------------------
     * 
     * Don't worry, I actually have a structure tutorial
     * mod already setup for you to check out! It's full
     * of comments on what does what and how to make structures.
     * 
     * Here's the link! https://github.com/TelepathicGrunt/StructureTutorialMod
     * 
     * Good luck and have fun modding!
     */
	public StrongholdStructureUA(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51427_1_)
	{
		super(p_i51427_1_);
	}


	@Override
	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ)
	{
		int maxDistance = UltraAmplified.UAConfig.strongholdSpawnrate.get();
		int minDistance = maxDistance - 10;
		if (maxDistance < 12)
		{
			minDistance = maxDistance - 1;
		}
		int k = x + maxDistance * spacingOffsetsX;
		int l = z + maxDistance * spacingOffsetsZ;
		int i1 = k < 0 ? k - maxDistance + 1 : k;
		int j1 = l < 0 ? l - maxDistance + 1 : l;
		int k1 = i1 / maxDistance;
		int l1 = j1 / maxDistance;
		((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 143523564);
		k1 = k1 * maxDistance;
		l1 = l1 * maxDistance;
		k1 = k1 + random.nextInt(maxDistance - minDistance);
		l1 = l1 + random.nextInt(maxDistance - minDistance);
		return new ChunkPos(k1, l1);
	}


	@Override
	public boolean canBeGenerated(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome)
	{

		ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
		if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z)
		{
			if ((UltraAmplified.UAConfig.strongholdSpawnrate.get() != 501) && chunkGen.hasStructure(biome, this))
			{
				return true;
			}
		}

		return false;
	}


	@Override
	public Structure.IStartFactory getStartFactory()
	{
		return StrongholdStructureUA.Start::new;
	}


	@Override
	public String getStructureName()
	{
		return UltraAmplified.MODID + ":stronghold";
	}


	@Override
	public int getSize()
	{
		return 8;
	}

	public static class Start extends StructureStart
	{
		public Start(Structure<?> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn)
		{
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}


		@Override
		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn)
		{
			StrongholdPiecesUA.prepareStructurePieces();
			StrongholdPiecesUA.EntranceStairs strongholdpieces$entrancestairs = new StrongholdPiecesUA.EntranceStairs(this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
			this.components.add(strongholdpieces$entrancestairs);
			strongholdpieces$entrancestairs.buildComponent(strongholdpieces$entrancestairs, this.components, this.rand);
			List<StructurePiece> list = strongholdpieces$entrancestairs.pendingChildren;

			while (!list.isEmpty())
			{
				int i = this.rand.nextInt(list.size());
				StructurePiece structurepiece = list.remove(i);
				structurepiece.buildComponent(strongholdpieces$entrancestairs, this.components, this.rand);
			}



			if (strongholdpieces$entrancestairs.strongholdPortalRoom == null)
			{
				MutableBoundingBox box = this.components.get(this.components.size()-1).getBoundingBox();
				StrongholdPiecesUA.Stronghold portalRoom = StrongholdPiecesUA.PortalRoom.createPiece(this.components, this.rand, box.minX, box.minY+1, box.minZ, Direction.NORTH);
				this.components.add(portalRoom);
				strongholdpieces$entrancestairs.pendingChildren.add(portalRoom);
				list = strongholdpieces$entrancestairs.pendingChildren;
				
				while (!list.isEmpty())
				{
					int i = this.rand.nextInt(list.size());
					StructurePiece structurepiece = list.remove(i);
					structurepiece.buildComponent(strongholdpieces$entrancestairs, this.components, this.rand);
				}
			}
			
			this.recalculateStructureSize();
			this.func_214626_a(this.rand, 100, 120);
			//			UltraAmplified.LOGGER.log(Level.DEBUG, "Stronghold | "+(chunkX*16)+" "+(chunkZ*16));
		}
	}
}