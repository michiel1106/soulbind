package bikerdbmid.soulbound.components.content.event;

import bikerdbmid.soulbound.components.*;
import bikerdbmid.soulbound.components.content.effect.*;
import bikerdbmid.soulbound.components.content.effect.custom.*;
import net.fabricmc.fabric.api.entity.event.v1.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;

public class ModEventHandler {
    public static void init() {

        ServerLivingEntityEvents.ALLOW_DAMAGE.register(ModEventHandler::allowDamage);


    }

    private static boolean allowDamage(LivingEntity entity, DamageSource damageSource, float v) {
        if (!damageSource.is(DamageTypeTags.IS_FALL)) return true;


        if (entity instanceof ServerPlayer plr) {
            Effect effect = ModComponents.SOULDATA.get(plr).getEffect();
            if (effect == null) return true;

            if (effect.equals(ModEffects.FEATHER)) {
                return false;
            }
        }



        return true;
    }
}
