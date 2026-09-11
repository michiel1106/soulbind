package bikerdbmid.soulbound.components.content.buffs.custom;

import bikerdbmid.soulbound.api.content.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;

public class EffectBuff extends Buff{
    String id;
    Holder<MobEffect> effect;
    int duration;
    int amplifier;

    public EffectBuff(String id, Holder<MobEffect> jumpBoost, int duration, int amplifier) {
        super(id);
        this.id = id;
        this.effect = jumpBoost;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    @Override
    public void tick(Player player, CompoundTag data) {
        super.tick(player, data);
        player.addEffect(new MobEffectInstance(effect, duration, amplifier));

    }
}
