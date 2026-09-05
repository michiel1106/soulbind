package bikerdbmid.soulbound.components.content.debuffs.custom;

import bikerdbmid.soulbound.api.content.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;

public class EffectDebuff extends DeBuff {
    EDistType type;
    String id;
    Holder<MobEffect> effect;
    int duration;
    int amplifier;

    public EffectDebuff(EDistType distType, String id, Holder<MobEffect> jumpBoost, int duration, int amplifier) {
        super(distType, id);
        this.type = distType;
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
