package com.telepathicgrunt.ultraamplifieddimension.mixin.structures;

import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.MineshaftPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(MineshaftPieces.Room.class)
public interface MineshaftRoomAccessor {
    @Accessor("connectedRooms")
    List<MutableBoundingBox> uad_getConnectedRooms();
}
