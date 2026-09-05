package bikerdbmid.soulbound;

import bikerdbmid.soulbound.commands.*;
import bikerdbmid.soulbound.commands.debug.*;
import bikerdbmid.soulbound.components.*;
import bikerdbmid.soulbound.components.content.*;
import bikerdbmid.soulbound.networking.*;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoulBound implements ModInitializer {
	public static final String MOD_ID = "soulbound";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModComponents.init();
		ModNetworking.init();
		ModCommands.init();
		DebugCommands.init();
		ModContentInit.init();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
