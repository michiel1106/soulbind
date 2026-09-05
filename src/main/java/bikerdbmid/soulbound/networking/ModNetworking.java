package bikerdbmid.soulbound.networking;

import bikerdbmid.soulbound.components.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.server.level.*;

public class ModNetworking {

    public static void init() {
        PayloadTypeRegistry.serverboundPlay().register(ActivatePowerPayload.TYPE, ActivatePowerPayload.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ActivatePowerPayload.TYPE, (payload, context) -> {
            ServerPlayer player = context.player();
            context.server().execute(() -> {
                ModComponents.SOULDATA.get(player).activatePower();
            });
        });
    }

}
