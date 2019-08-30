package net.telepathicgrunt.ultraamplified.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AmplifiedPortalTileEntity extends TileEntity {
   public AmplifiedPortalTileEntity(TileEntityType<?> p_i48283_1_) {
      super(p_i48283_1_);
   }

   public AmplifiedPortalTileEntity() {
      this(TileEntityType.END_PORTAL);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean shouldRenderFace(Direction face) {
      return face == Direction.UP;
   }
}