package bikerdbmid.soulbound.components.content.powers.custom;

import bikerdbmid.soulbound.api.content.EDistType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class IceArmorPower extends Power {
    private final int interval;

    public IceArmorPower(EDistType distType, String id, int interval) {
        super(distType, id);
        this.interval = interval;
    }

    @Override
    public void onApply(Player player, CompoundTag data) {
        data.putInt("stacks", 0);
        data.putInt("ticks", 0);
    }

    @Override
    public void tick(Player player, CompoundTag data) {
        int ticks = data.getInt("ticks").orElseGet(() -> 0) + 1;
        data.putInt("ticks", ticks);
        if (ticks % interval == 0) {
            int stacks = Math.min(data.getInt("stacks").get() + 1, 5);
            data.putInt("stacks", stacks);
            player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, interval + 5, stacks));
        }
    }

    @Override
    public void onRemove(Player player, CompoundTag data) {
        player.removeEffect(MobEffects.RESISTANCE);
    }
}
