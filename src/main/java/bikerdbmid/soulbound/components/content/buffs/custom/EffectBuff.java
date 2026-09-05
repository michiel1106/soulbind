package bikerdbmid.soulbound.components.content.buffs.custom;

import bikerdbmid.soulbound.api.content.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;

public class EffectBuff extends Buff {


    public EffectBuff(EDistType eDistType, String id) {
        super(eDistType, id);
    }

    @Override
    public void tick(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 20, 1));
    }
}
