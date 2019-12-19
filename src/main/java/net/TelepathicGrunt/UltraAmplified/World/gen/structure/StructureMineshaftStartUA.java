package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.gen.structure.StructureMineshaftPiecesUA.Room;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

public class StructureMineshaftStartUA extends StructureStart
{
    private MapGenMineshaftUA.Type mineShaftType;

    public StructureMineshaftStartUA()
    {
    }

    public StructureMineshaftStartUA(World p_i47149_1_, Random p_i47149_2_, int p_i47149_3_, int p_i47149_4_, MapGenMineshaftUA.Type p_i47149_5_)
    {
    	super(p_i47149_3_, p_i47149_4_);
        this.mineShaftType = p_i47149_5_;
        Room structuremineshaftpieces$room = new StructureMineshaftPiecesUA.Room(0, p_i47149_2_, (p_i47149_3_ << 4) + 2, (p_i47149_4_ << 4) + 2, this.mineShaftType);
        
        //makes the normal dirt room mineshafts spawn between 150 and 240
        if(structuremineshaftpieces$room.getBoundingBox().maxY < 100) {
        	structuremineshaftpieces$room.getBoundingBox().offset(0, 150 + p_i47149_2_.nextInt(70), 0);
        }
        
        this.components.add(structuremineshaftpieces$room);
        structuremineshaftpieces$room.buildComponent(structuremineshaftpieces$room, this.components, p_i47149_2_);
       
        this.updateBoundingBox();
    }
}
