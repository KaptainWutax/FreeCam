package kaptainwutax.freecam.event;

import org.lwjgl.input.Mouse;

import kaptainwutax.freecam.init.InitKeyBinding;
import kaptainwutax.freecam.utility.Constant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = Constant.MOD_ID, value = Side.CLIENT)
public class EventViewRender {
	
	private static float prevYaw = 0;
	private static float prevPitch = 0;
	private static float prevRoll = 0;
	private static int prevScrollWheelKnot = 0;
	
	private static float originalRotationYaw = 0;
	private static float originalRotationPitch = 0;
	private static float originalHeadRotationYaw = 0;
	private static int originalHeldSlot = 0;
	private static float originalFOV = 70;
	
	private static boolean isAnchored = false;
	private static boolean firstPress = true;
	
	@SubscribeEvent
	public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {

		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP player = mc.player;
		GameSettings gameSettings = mc.gameSettings;
		
		//Apply roll effect
		if(InitKeyBinding.ROLL_L.isKeyDown()) {
			prevRoll--;
		}
		
		if(InitKeyBinding.ROLL_R.isKeyDown()) {
			prevRoll++;
		}
		
		if(InitKeyBinding.ROLL_RESET.isKeyDown()) {
			prevRoll = 0;
		}
		
		event.setRoll(prevRoll);
		
		//Check for "freecam" mode
		if(InitKeyBinding.ANCHOR.isPressed()) {
			isAnchored = !isAnchored;
		}
	
		if(isAnchored) {				
			if(firstPress) {
				//Stores the original rotations before engaging "freecam" mode
				originalRotationYaw = player.rotationYaw;
				originalRotationPitch = player.rotationPitch;
				originalHeadRotationYaw = player.rotationYawHead;
				originalFOV = gameSettings.fovSetting;
				originalHeldSlot = player.inventory.currentItem;
			}
			
			//Locks the player rotation with the mouse
			player.rotationYaw = originalRotationYaw;
			player.rotationPitch = originalRotationPitch;
			player.rotationYawHead = originalHeadRotationYaw;
			
			//Rotates the camera around the player
			double sensitivity = Math.pow(gameSettings.mouseSensitivity * 0.6F + 0.2F, 3) * 8.0F;
			
			double deltaX = mc.mouseHelper.deltaX * sensitivity * 0.15D;
			double deltaY = -mc.mouseHelper.deltaY * sensitivity * 0.15D;
			
			event.setYaw((float)deltaX + prevYaw + originalRotationYaw - 180);
			event.setPitch((float)deltaY + prevPitch + player.rotationPitch);
			prevYaw = (float)deltaX + prevYaw;
			prevPitch = (float)deltaY + prevPitch;
			
			//Strafe is "freecam" mode
			if(gameSettings.keyBindLeft.isKeyDown()) {
				originalRotationYaw -= sensitivity;
				originalHeadRotationYaw -= sensitivity;	
			}
			if(gameSettings.keyBindRight.isKeyDown()) {
				originalRotationYaw += sensitivity;
				originalHeadRotationYaw += sensitivity;
			}
			
			//Cancels scrolling through the hotbar with the mouse scrollwheel
			player.inventory.currentItem = originalHeldSlot;
			
			//Does the scrolling logic, affects the field of view
			prevScrollWheelKnot = 0;
			prevScrollWheelKnot += Mouse.getDWheel();
			
			while (prevScrollWheelKnot >= 120) {
				prevScrollWheelKnot-=120;
				gameSettings.fovSetting -= sensitivity * 4;
			}
			while (prevScrollWheelKnot <= -120) {
				prevScrollWheelKnot+=120; 
				gameSettings.fovSetting += sensitivity * 4;
			}
			
			if(gameSettings.fovSetting < 20) {
				gameSettings.fovSetting = 20;
			}
			
			if(gameSettings.fovSetting > 140) {
				gameSettings.fovSetting = 140;
			}
			
			if(gameSettings.fovSetting > 120 && gameSettings.thirdPersonView == 0) {
				gameSettings.thirdPersonView = 1;
				gameSettings.fovSetting = 40;
			}
			
			if(gameSettings.fovSetting < 40 && gameSettings.thirdPersonView == 1) {
				gameSettings.thirdPersonView = 0;
				gameSettings.fovSetting = 120;
			}
			
			//"freecam" mode has already engaged
			firstPress = false;			
		} else {
			//Resets everything for the next use
			firstPress = true;
			prevYaw = 0;
			prevPitch = 0;	
			gameSettings.fovSetting = originalFOV;
		}
		
	}
	
}
