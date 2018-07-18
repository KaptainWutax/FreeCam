package kaptainwutax.freecam.event;

import kaptainwutax.freecam.init.InitKeyBinding;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class EventMod {

	public static void preInitialize(FMLPreInitializationEvent event) {
		//As the mod uses "clientSideOnly = true", it will always run on the client side
		InitKeyBinding.registerKeyBindings();
	}
	
}
