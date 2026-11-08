package bikerdbmid.soulbound.util;

import bikerdbmid.soulbound.components.*;
import bikerdbmid.soulbound.components.IComponents.*;
import bikerdbmid.soulbound.components.content.debuffs.*;
import bikerdbmid.soulbound.components.content.effect.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import org.jetbrains.annotations.*;

public class LogicUtils {


    public static @Nullable MobEffectInstance poisonPower$GetInflictableEffect(ServerPlayer attacker, ServerPlayer victim) {
        RandomSource random = attacker.getRandom();
        ISoulDataComponent iSoulDataComponent = ModComponents.SOULDATA.get(attacker);

        if (!iSoulDataComponent.getEffect().equals(ModEffects.POISON)) {
            return null;
        }

        if (attacker.getMainHandItem().isEmpty()) {
            if (random.nextFloat() <= 0.3f) {
                return new MobEffectInstance(MobEffects.POISON, 200, 0);
            }
        } else if (attacker.getMainHandItem().equals(ItemTags.SWORDS)) {
            if (iSoulDataComponent.isDebuffActive(ModDebuffs.REGEN_ON_SWORD_HIT_HIGH) || iSoulDataComponent.isDebuffActive(ModDebuffs.REGEN_ON_SWORD_HIT_MEDIUM)) {
                if (random.nextFloat() <= 0.02f) {
                    return new MobEffectInstance(MobEffects.REGENERATION, 200, 0);
                }
            }
        }

        return null;

    }

}
