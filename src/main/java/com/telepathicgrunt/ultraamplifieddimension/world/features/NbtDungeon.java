package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.mixin.features.TemplateInvoker;
import com.telepathicgrunt.ultraamplifieddimension.utils.GeneralUtils;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.NbtDungeonConfig;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.IClearable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.Level;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class NbtDungeon extends Feature<NbtDungeonConfig>{

    public NbtDungeon(Codec<NbtDungeonConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, NbtDungeonConfig config) {

        ResourceLocation nbtRL = GeneralUtils.getRandomEntry(config.nbtResourcelocationsAndWeights, random);

        TemplateManager structureManager = world.getWorld().getStructureTemplateManager();
        Template template = structureManager.getTemplateDefaulted(nbtRL);
        Rotation rotation = Rotation.randomRotation(random);

        // Rotated blockpos for the nbt's sizes to be used later. Ignore Y
        BlockPos fullLengths = new BlockPos(
                Math.abs(template.getSize().rotate(rotation).getX()),
                Math.abs(template.getSize().rotate(rotation).getY()),
                Math.abs(template.getSize().rotate(rotation).getZ()));

        BlockPos halfLengths = new BlockPos(
                fullLengths.getX() / 2,
                fullLengths.getY() / 2,
                fullLengths.getZ() / 2);

        BlockPos.Mutable mutable = new BlockPos.Mutable().setPos(position);
        IChunk cachedChunk = world.getChunk(mutable);

        int xMin = -halfLengths.getX() + 1;
        int xMax = halfLengths.getX() - 1;
        int zMin = -halfLengths.getZ() + 1;
        int zMax = halfLengths.getZ() - 1;
        int validOpenings = 0;
        int ceilingOpenings = 0;
        int ceiling = template.getSize().getY();

        for (int x = xMin - 1; x <= xMax + 1; ++x) {
            for (int z = zMin - 1; z <= zMax + 1; ++z) {
                for (int y = -2; y <= ceiling + 1; ++y) {
                    mutable.setPos(position).move(x, y, z);
                    if(mutable.getX() >> 4 != cachedChunk.getPos().x || mutable.getZ() >> 4 != cachedChunk.getPos().z)
                        cachedChunk = world.getChunk(mutable);

                    BlockState state = cachedChunk.getBlockState(mutable);

                    // Dungeons cannot touch fluids if set to air mode and reverse if opposite
                    if(config.airRequirementIsNowWater ? state.isAir() : !state.getFluidState().isEmpty()){
                        return false;
                    }
                    // Floor must be complete
                    else if(!GeneralUtils.isFullCube(world, mutable, state)){
                        if (y == -1) {
                            return false;
                        }
                        else if(state.isIn(BlockTags.LEAVES)){
                            continue; // ignore leaves
                        }
                        else if (y == ceiling) {
                            ceilingOpenings++;
                        }
                    }

                    // Check only along wall bottoms for openings
                    if ((x == xMin || x == xMax || z == zMin || z == zMax) && y == 0 && isValidNonSolidBlock(config, state))
                    {
                        BlockState aboveState = cachedChunk.getBlockState(mutable.move(Direction.UP));
                        if(config.airRequirementIsNowWater ?
                            !aboveState.getFluidState().isEmpty() :
                            aboveState.isAir())
                        {
                            validOpenings++;
                        }
                        mutable.move(Direction.DOWN);
                    }

                    // Too much open space. Quit
                    if(validOpenings > config.maxAirSpace || ceilingOpenings >= config.maxAirSpace){
                        return false;
                    }
                }
            }
        }

        // offset the dungeon such as ocean dungeons down 1
        position = position.up(config.structureYOffset);

        // Check if we meet minimum for open space.
        if (validOpenings >= config.minAirSpace) {
            //UltraAmplifiedDimension.LOGGER.log(Level.INFO, nbtRL + " at X: "+position.getX() +", "+position.getY()+", "+position.getZ());
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(rotation).setCenterOffset(halfLengths).setIgnoreEntities(false);
            config.processor.get().func_242919_a().forEach(placementsettings::addProcessor); // add all processors
            addBlocksToWorld(template, world, chunkGenerator, mutable.setPos(position).move(-halfLengths.getX(), -1, -halfLengths.getZ()), placementsettings, 2, random, config);
            spawnLootBlocks(world, random, position, config, fullLengths, halfLengths, mutable);
            return true;
        }

        return false;
    }

    private void spawnLootBlocks(ISeedReader world, Random random, BlockPos position, NbtDungeonConfig config, BlockPos fullLengths, BlockPos halfLengths, BlockPos.Mutable mutable) {
        boolean isPlacingChestLikeBlock = config.lootBlock.getBlock() instanceof ChestBlock;

        // Add chests that are wall based
        for(int currentChestCount = 0; currentChestCount < config.maxNumOfChests; ++currentChestCount) {
            for (int currentChestAttempt = 0; currentChestAttempt < fullLengths.getX() + fullLengths.getZ() + halfLengths.getY(); ++currentChestAttempt) {
                if (currentChestCount == config.maxNumOfChests) {
                    return; // early exit
                }

                mutable.setPos(position).move(
                        random.nextInt(Math.max(fullLengths.getX() - 2, 1)) - halfLengths.getX() + 1,
                        random.nextInt(Math.max(fullLengths.getY() - 1, 1)),
                        random.nextInt(Math.max(fullLengths.getZ() - 2, 1)) - halfLengths.getZ() + 1);

                BlockState currentBlock = world.getBlockState(mutable);
                if (isValidNonSolidBlock(config, currentBlock)) {
                    if(world.getBlockState(mutable.move(Direction.DOWN)).isSolidSide(world, mutable, Direction.UP)){
                        mutable.move(Direction.UP);
                        boolean isOnWall = false;

                        for(Direction direction : Direction.Plane.HORIZONTAL){

                            mutable.move(direction);
                            BlockState neighboringState = world.getBlockState(mutable);
                            mutable.move(direction.getOpposite());

                            if(isPlacingChestLikeBlock && neighboringState.getBlock() instanceof ChestBlock){
                                // Only connect to single chests
                                if(neighboringState.get(ChestBlock.TYPE) == ChestType.SINGLE){

                                    // Set current chest direction to the side for the double chest connection.
                                    Direction doubleChestDirection = direction.rotateY();
                                    boolean flippedDirections = false;
                                    BlockState blockState = world.getBlockState(mutable.offset(direction));

                                    // Face opposite direction if facing wall.
                                    if(GeneralUtils.isFullCube(world, mutable, blockState) && !(blockState.getBlock() instanceof SpawnerBlock)){
                                        doubleChestDirection = doubleChestDirection.getOpposite();
                                        flippedDirections = true;
                                    }

                                    world.setBlockState(mutable,
                                            config.lootBlock
                                                .with(ChestBlock.WATERLOGGED, currentBlock.getFluidState().isTagged(FluidTags.WATER))
                                                .with(ChestBlock.FACING, doubleChestDirection)
                                                .with(ChestBlock.TYPE, flippedDirections ? ChestType.LEFT : ChestType.RIGHT), 2);
                                    LockableLootTileEntity.setLootTable(world, random, mutable, config.chestResourceLocation);

                                    BlockState blockBelow = world.getBlockState(mutable.down());
                                    if(blockBelow.hasProperty(SlabBlock.TYPE)){
                                        world.setBlockState(mutable.down(), blockBelow.with(SlabBlock.TYPE, SlabType.DOUBLE), 3);
                                    }

                                    // Set neighboring chest to face same way too
                                    world.setBlockState(mutable.move(direction), Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, doubleChestDirection).with(ChestBlock.TYPE, flippedDirections ? ChestType.RIGHT : ChestType.LEFT), 2);
                                    currentChestCount++;
                                    isOnWall = false; // Skip wall code as we already placed chest
                                    break;
                                }
                            }
                            else if(GeneralUtils.isFullCube(world, mutable, neighboringState) && !(neighboringState.getBlock() instanceof SpawnerBlock)){
                                isOnWall = true;
                            }
                        }

                        // Is not next to another chest.
                        if(isOnWall){
                            BlockState lootBlock = config.lootBlock;
                            if(lootBlock.hasProperty(BlockStateProperties.WATERLOGGED)){
                                lootBlock.with(BlockStateProperties.WATERLOGGED, currentBlock.getFluidState().isTagged(FluidTags.WATER));
                            }
                            if(lootBlock.hasProperty(BlockStateProperties.HORIZONTAL_FACING)){
                                lootBlock = StructurePiece.correctFacing(world, mutable, lootBlock);
                            }

                            // Set chest to face away from wall.
                            world.setBlockState(mutable, lootBlock, 2);
                            LockableLootTileEntity.setLootTable(world, random, mutable, config.chestResourceLocation);

                            BlockState blockBelow = world.getBlockState(mutable.down());
                            if(blockBelow.hasProperty(SlabBlock.TYPE)){
                                world.setBlockState(mutable.down(), blockBelow.with(SlabBlock.TYPE, SlabType.DOUBLE), 3);
                            }

                            currentChestCount++;
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds blocks and entities from this structure to the given world.
     */
    public void addBlocksToWorld(Template structure, ISeedReader world, ChunkGenerator chunkGenerator, BlockPos pos, PlacementSettings placementIn, int flags, Random random, NbtDungeonConfig config) {
        TemplateInvoker structureAccessor = ((TemplateInvoker) structure);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        if (!structureAccessor.uad_getBlocks().isEmpty()) {
            List<Template.BlockInfo> list = placementIn.func_237132_a_(structureAccessor.uad_getBlocks(), pos).func_237157_a_();
            if ((!list.isEmpty() || !placementIn.getIgnoreEntities() && !structureAccessor.uad_getEntities().isEmpty()) && structureAccessor.uad_getSize().getX() >= 1 && structureAccessor.uad_getSize().getY() >= 1 && structureAccessor.uad_getSize().getZ() >= 1) {
                MutableBoundingBox mutableboundingbox = placementIn.getBoundingBox();
                List<BlockPos> list1 = Lists.newArrayListWithCapacity(placementIn.func_204763_l() ? list.size() : 0);
                List<Pair<BlockPos, CompoundNBT>> list2 = Lists.newArrayListWithCapacity(list.size());
                int minX = Integer.MAX_VALUE;
                int minY = Integer.MAX_VALUE;
                int minZ = Integer.MAX_VALUE;
                int maxX = Integer.MIN_VALUE;
                int maxY = Integer.MIN_VALUE;
                int maxZ = Integer.MIN_VALUE;

                for (Template.BlockInfo template$blockinfo : Template.func_237145_a_(world, pos, pos, placementIn, list)) {
                    BlockPos blockpos = template$blockinfo.pos;

                    if (mutableboundingbox == null || mutableboundingbox.isVecInside(blockpos)) {
                        FluidState ifluidstate = placementIn.func_204763_l() ? world.getFluidState(blockpos) : null;
                        BlockState blockstate = template$blockinfo.state.mirror(placementIn.getMirror()).rotate(placementIn.getRotation());
                        if (template$blockinfo.nbt != null) {
                            TileEntity blockentity = world.getTileEntity(blockpos);
                            IClearable.clearObj(blockentity);
                            world.setBlockState(blockpos, Blocks.BARRIER.getDefaultState(), 3);
                        }

                        BlockState originalBlockState = world.getBlockState(blockpos);
                        if (!originalBlockState.hasTileEntity())
                        {
                            // No floating chests or spawners
                            BlockState aboveState = world.getBlockState(mutable.setPos(blockpos).move(Direction.UP));

                            boolean forcePlaceBlock = false;
                            if(config.blocksToAlwaysPlace.isPresent()){
                                forcePlaceBlock = config.blocksToAlwaysPlace.get().contains(blockstate);
                            }

                            if(isNotSpawnerOrChest(aboveState) &&
                                    (config.replaceAir ||
                                    forcePlaceBlock ||
                                    GeneralUtils.isFullCube(world, mutable, originalBlockState) ||
                                    blockstate.hasTileEntity()))
                            {
                                // Attempt to let leaves stay in the dungeon space and not be cut off
                                if(!(isValidNonSolidBlock(config, blockstate) && originalBlockState.isIn(BlockTags.LEAVES))){
                                    world.setBlockState(blockpos, blockstate, 3);

                                    minX = Math.min(minX, blockpos.getX());
                                    minY = Math.min(minY, blockpos.getY());
                                    minZ = Math.min(minZ, blockpos.getZ());
                                    maxX = Math.max(maxX, blockpos.getX());
                                    maxY = Math.max(maxY, blockpos.getY());
                                    maxZ = Math.max(maxZ, blockpos.getZ());
                                    list2.add(Pair.of(blockpos, template$blockinfo.nbt));

                                    if (template$blockinfo.nbt != null){
                                        setBlockEntity(world, placementIn, random, config, template$blockinfo, blockpos, blockstate);
                                    }

                                    if (ifluidstate != null && blockstate.getBlock() instanceof ILiquidContainer) {
                                        ((ILiquidContainer) blockstate.getBlock()).receiveFluid(world, blockpos, blockstate, ifluidstate);
                                        if (!ifluidstate.isSource()) {
                                            list1.add(blockpos);
                                        }
                                    }
                                }
                            }

                            // Prevent plants remaining at edge of dungeons like bamboo which then breaks as dungeon floor isn't valid for bamboo.
                            else if(!isValidNonSolidBlock(config, blockstate) && !originalBlockState.isIn(BlockTags.LEAVES) && !originalBlockState.isSolid() && originalBlockState.getFluidState().isEmpty()){
                                world.setBlockState(blockpos, config.airRequirementIsNowWater ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState(), 3);

                                BlockPos.Mutable mutable1 = new BlockPos.Mutable().setPos(blockpos);
                                BlockState blockState = world.getBlockState(mutable1.move(Direction.UP));

                                while(mutable1.getY() < chunkGenerator.getMaxBuildHeight() && !blockState.isValidPosition(world, mutable1)){
                                    world.setBlockState(mutable1, Blocks.AIR.getDefaultState(), 3);
                                    blockState = world.getBlockState(mutable1.move(Direction.UP));
                                }
                            }
                        }
                    }
                }

                // fillFluidStates(world, list1);

                if (minX <= maxX) {
                    if (!placementIn.func_215218_i()) {
                        VoxelShapePart voxelshapepart = new BitSetVoxelShapePart(maxX - minX + 1, maxY - minY + 1, maxZ - minZ + 1);
                        setVoxelShapeParts(world, flags, list2, minX, minY, minZ, voxelshapepart);
                    }

                    placeBlocks(world, placementIn, list2);
                }

                if (!placementIn.getIgnoreEntities()) {
                    structureAccessor.uad_invokeSpawnEntities(world, pos, placementIn);
                }
            }
        }
    }

    private void setBlockEntity(IServerWorld world, PlacementSettings placementIn, Random random, NbtDungeonConfig config, Template.BlockInfo template$blockinfo, BlockPos blockpos, BlockState blockstate) {
        TileEntity blockentity1 = world.getTileEntity(blockpos);
        if (blockentity1 != null) {
            template$blockinfo.nbt.putInt("x", blockpos.getX());
            template$blockinfo.nbt.putInt("y", blockpos.getY());
            template$blockinfo.nbt.putInt("z", blockpos.getZ());
            blockentity1.read(template$blockinfo.state, template$blockinfo.nbt);
            blockentity1.mirror(placementIn.getMirror());
            blockentity1.rotate(placementIn.getRotation());

            if (blockentity1 instanceof MobSpawnerTileEntity) {
                EntityType<?> entity = GeneralUtils.getRandomEntry(config.spawnerResourcelocationsAndWeights, random);

                if(entity != null){
                    ((MobSpawnerTileEntity)blockentity1).getSpawnerBaseLogic().setEntityType(entity);
                }
                else{
                    UltraAmplifiedDimension.LOGGER.log(Level.WARN, "EntityType in a dungeon does not exist in registry!");
                }
            }
            else if(blockentity1 instanceof LockableLootTileEntity){
                if(blockstate.isIn(Blocks.CHEST)){
                    world.setBlockState(blockpos, StructurePiece.correctFacing(world, blockpos, Blocks.CHEST.getDefaultState()), 2);
                }
                LockableLootTileEntity.setLootTable(world, random, blockpos, config.chestResourceLocation);
            }
        }
    }

    private void fillFluidStates(IServerWorld world, List<BlockPos> blockPosList) {
        boolean flag = true;
        while (flag && !blockPosList.isEmpty()) {
            flag = false;
            Iterator<BlockPos> iterator = blockPosList.iterator();

            while (iterator.hasNext()) {
                BlockPos blockpos2 = iterator.next();
                BlockPos blockpos3 = blockpos2;
                FluidState ifluidstate2 = world.getFluidState(blockpos2);

                for (int directionIndex = 1; directionIndex < 6 && !ifluidstate2.isSource(); ++directionIndex) {
                    // Skip down direction
                    Direction direction = Direction.byIndex(directionIndex);
                    BlockPos blockpos1 = blockpos3.offset(direction);
                    FluidState ifluidstate1 = world.getFluidState(blockpos1);
                    if (ifluidstate1.getActualHeight(world, blockpos1) > ifluidstate2.getActualHeight(world, blockpos3) ||
                            ifluidstate1.isSource() && !ifluidstate2.isSource())
                    {
                        ifluidstate2 = ifluidstate1;
                        blockpos3 = blockpos1;
                    }
                }

                if (ifluidstate2.isSource()) {
                    BlockState blockstate2 = world.getBlockState(blockpos2);
                    Block block = blockstate2.getBlock();
                    if (block instanceof ILiquidContainer) {
                        ((ILiquidContainer) block).receiveFluid(world, blockpos2, blockstate2, ifluidstate2);
                        flag = true;
                        iterator.remove();
                    }
                }
            }
        }
    }

    private boolean isNotSpawnerOrChest(BlockState state){
        return !state.isIn(Blocks.SPAWNER) && !(state.getBlock() instanceof AbstractChestBlock);
    }

    protected static void placeBlocks(IServerWorld world, PlacementSettings placementIn, List<Pair<BlockPos, CompoundNBT>> list2) {
        for (Pair<BlockPos, CompoundNBT> pair : list2) {
            BlockPos blockpos4 = pair.getFirst();

            if (!placementIn.func_215218_i()) {
                BlockState blockstate1 = world.getBlockState(blockpos4);
                BlockState blockstate3 = Block.getValidBlockForPosition(blockstate1, world, blockpos4);
                if (blockstate1 != blockstate3) {
                    world.setBlockState(blockpos4, blockstate3, 3);
                }

                world.func_230547_a_(blockpos4, blockstate3.getBlock());
            }

            if (pair.getSecond() != null) {
                TileEntity blockentity2 = world.getTileEntity(blockpos4);
                if (blockentity2 != null) {
                    blockentity2.markDirty();
                }
            }
        }
    }

    protected static void setVoxelShapeParts(IServerWorld world, int flags, List<Pair<BlockPos, CompoundNBT>> list2, int x, int y, int z, VoxelShapePart voxelshapepart) {
        for (Pair<BlockPos, CompoundNBT> pair1 : list2) {
            BlockPos blockpos5 = pair1.getFirst();
            voxelshapepart.setFilled(blockpos5.getX() - x, blockpos5.getY() - y, blockpos5.getZ() - z, true, true);
        }

        Template.func_222857_a(world, flags, voxelshapepart, x, y, z);
    }

    private boolean isValidNonSolidBlock(NbtDungeonConfig config, BlockState state){
        if(config.airRequirementIsNowWater){
            return !state.getFluidState().isEmpty();
        }
        return state.isAir();
    }
}
