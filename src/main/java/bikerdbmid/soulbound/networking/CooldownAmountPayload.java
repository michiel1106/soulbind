package bikerdbmid.soulbound.networking;

import bikerdbmid.soulbound.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;

public record CooldownAmountPayload(int remaining, int total) implements CustomPacketPayload {
    public static final Type<CooldownAmountPayload> TYPE =
            new Type<>(SoulBound.id("cooldown_amount"));

    public static final StreamCodec<FriendlyByteBuf, CooldownAmountPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, CooldownAmountPayload::remaining,
                    ByteBufCodecs.VAR_INT, CooldownAmountPayload::total,
                    CooldownAmountPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
