package net.TelepathicGrunt.UltraAmplified.Utils;

import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MinecraftError;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


//Attempt at showing load progress on world loading screen

@SideOnly(Side.CLIENT)
public class LoadingScreenRenderProgress extends LoadingScreenRenderer{

	public LoadingScreenRenderProgress(Minecraft mcIn) {
		super(mcIn);
		//System.out.println("******************* initialized");
	}
	
	/*
	private MinecraftServer test;
	//Displays a string on the loading screen supposed to indicate what is being done currently.
     
	@Override
    public void displayLoadingString(String message)
    {
		System.out.println("******************* displaying");
	    test = FMLCommonHandler.instance().getMinecraftServerInstance();
		
        if (!this.loadingSuccess)
        {
            throw new MinecraftError();
        }
        else
        {
            this.systemTime = 0L;
            this.message = "Creating world: "+test.percentDone+"%";
            this.setLoadingProgress(-1);
            this.systemTime = 0L;
        }
    }
	*/
}
