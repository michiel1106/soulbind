package bikerdbmid.soulbound.networking;

import bikerdbmid.soulbound.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;

public record ActivatePowerPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ActivatePowerPayload> TYPE =
            new CustomPacketPayload.Type<>(SoulBound.id("activate_power"));

    public static final StreamCodec<FriendlyByteBuf, ActivatePowerPayload> STREAM_CODEC =
            StreamCodec.unit(new ActivatePowerPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
