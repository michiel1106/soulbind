package bikerdbmid.soulbound.components.content.powers.custom;

import bikerdbmid.soulbound.api.content.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.phys.*;

import java.util.*;

public class DashPower extends DelayPower {

    private static final double STRENGTH = 1.5;

    public DashPower(String id, int cooldown) {
        super(id, cooldown);
    }

    @Override
    protected void doPower(Player player, CompoundTag data) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        Vec3 look = serverPlayer.getLookAngle();
        serverPlayer.setDeltaMovement(look.scale(STRENGTH));
        serverPlayer.hurtMarked = true;
        serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));

    }
}