package bikerdbmid.soulbound.client;

import bikerdbmid.soulbound.*;
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


	@Override
	public void onInitializeClient() {
		KeyMappingRegistryImpl.registerKeyMapping(usePowerKey);

		ClientTickEvents.START_LEVEL_TICK.register((client) -> {
			if (usePowerKey.consumeClick()) {
				ClientPlayNetworking.send(new ActivatePowerPayload());
			}


		});

	}
}