package bikerdbmid.soulbound.client;

import bikerdbmid.soulbound.*;
import bikerdbmid.soulbound.client.screen.*;
import bikerdbmid.soulbound.networking.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.impl.client.keymapping.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import org.lwjgl.glfw.*;

import javax.swing.text.*;

public class SoulBoundClient implements ClientModInitializer {
	public static KeyMapping.Category category = KeyMapping.Category.register(SoulBound.id("soulbound"));

	KeyMapping usePowerKey = new KeyMapping("soulbound.usepower.key", GLFW.GLFW_KEY_N, category);
	KeyMapping testKey = new KeyMapping("soulbound.testkey.key", GLFW.GLFW_KEY_K, category);


	@Override
	public void onInitializeClient() {
		KeyMappingRegistryImpl.registerKeyMapping(usePowerKey);
		KeyMappingRegistryImpl.registerKeyMapping(testKey);

		ClientTickEvents.START_LEVEL_TICK.register((client) -> {
			if (usePowerKey.consumeClick()) {
				ClientPlayNetworking.send(new ActivatePowerPayload());
			}
			if (testKey.consumeClick()) {
				Minecraft.getInstance().setScreenAndShow(new EffectSelectionScreen());
			}
		});



	}
}