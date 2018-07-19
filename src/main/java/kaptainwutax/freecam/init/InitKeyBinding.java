package kaptainwutax.freecam.init;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class InitKeyBinding {

	public static KeyBinding ANCHOR = new KeyBinding("key.anchor.description", Keyboard.KEY_Z, "key.freecam.category");
	public static KeyBinding ROLL_L = new KeyBinding("key.roll_l.description", Keyboard.KEY_O, "key.freecam.category");
	public static KeyBinding ROLL_R = new KeyBinding("key.roll_r.description", Keyboard.KEY_P, "key.freecam.category");
	public static KeyBinding ROLL_RESET = new KeyBinding("key.roll_reset.description", Keyboard.KEY_I, "key.freecam.category");
	
	public static void registerKeyBindings() {
		 ClientRegistry.registerKeyBinding(ANCHOR);
		 ClientRegistry.registerKeyBinding(ROLL_L);
		 ClientRegistry.registerKeyBinding(ROLL_R);
		 ClientRegistry.registerKeyBinding(ROLL_RESET);			 
	}
	
}
