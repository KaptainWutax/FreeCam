package kaptainwutax.freecam;

import kaptainwutax.freecam.event.EventMod;
import kaptainwutax.freecam.utility.Constant;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constant.MOD_ID, name = Constant.MOD_NAME, version = Constant.MOD_VERSION, clientSideOnly = true, serverSideOnly = false)
public class FreeCam {

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		EventMod.preInitialize(event);
	}
	
}
