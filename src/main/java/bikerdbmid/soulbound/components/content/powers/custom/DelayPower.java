package bikerdbmid.soulbound.components.content.powers.custom;

import bikerdbmid.soulbound.api.content.*;
import bikerdbmid.soulbound.networking.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;

public abstract class DelayPower extends Power {
    private static final String COOLDOWN_KEY = "cooldown";

    private final int cooldownTicks;

    public DelayPower(String id, int cooldownTicks) {
        super(id);
        this.cooldownTicks = cooldownTicks;
    }

    @Override
    public void onApply(Player player, CompoundTag data) {
        super.onApply(player, data);
        data.putInt(COOLDOWN_KEY, 0);
    }

    @Override
    public boolean onUse(Player player, CompoundTag data) {
        if (isOnCooldown(data)) {
            return false;
        }

        doPower(player, data);
        data.putInt(COOLDOWN_KEY, cooldownTicks);

        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(serverPlayer,
                    new CooldownAmountPayload(cooldownTicks, cooldownTicks));
        }
        return true;
    }

    @Override
    public void onRemove(Player player, CompoundTag data) {
        super.onRemove(player, data);
        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(serverPlayer,
                    new CooldownAmountPayload(0, 1));
        }
    }

    @Override
    public void tick(Player player, CompoundTag data) {
        super.tick(player, data);

        int remaining = getRemainingTicks(data);
        if (remaining > 0) {
            data.putInt(COOLDOWN_KEY, remaining - 1);
        }
    }

    protected abstract void doPower(Player player, CompoundTag data);

    public boolean isOnCooldown(CompoundTag data) {
        return getRemainingTicks(data) > 0;
    }

    public int getRemainingTicks(CompoundTag data) {
        return data.getInt(COOLDOWN_KEY).orElse(0);
    }
}
