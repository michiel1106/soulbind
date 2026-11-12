package bikerdbmid.soulbound.components.content.event;

import bikerdbmid.soulbound.components.*;
import bikerdbmid.soulbound.components.Components.*;
import bikerdbmid.soulbound.components.IComponents.*;
import bikerdbmid.soulbound.components.content.debuffs.*;
import bikerdbmid.soulbound.components.content.effect.*;
import bikerdbmid.soulbound.components.content.effect.custom.*;
import bikerdbmid.soulbound.util.*;
import net.fabricmc.fabric.api.entity.event.v1.*;
import net.minecraft.core.component.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;

public class ModEventHandler {
    public static void init() {

        ServerLivingEntityEvents.ALLOW_DAMAGE.register(ModEventHandler::allowDamage);
        ServerLivingEntityEvents.AFTER_DAMAGE.register(ModEventHandler::afterDamage);

    }

    private static void afterDamage(LivingEntity victim, DamageSource damageSource, float baseDamageTaken, float damageTaken, boolean blocked) {
        Entity player = damageSource.getEntity();

        if (player instanceof ServerPlayer attacker) {
            if (victim instanceof ServerPlayer victim1) {
                MobEffectInstance mobEffectInstance = LogicUtils.poisonPower$GetInflictableEffect(attacker, victim1);

                if (mobEffectInstance != null) {
                    victim1.addEffect(mobEffectInstance);
                }
            }
        }

        if (victim instanceof ServerPlayer serverPlayer) {

        }

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
