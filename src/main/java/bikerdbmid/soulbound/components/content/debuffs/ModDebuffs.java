package bikerdbmid.soulbound.components.content.debuffs;

import bikerdbmid.soulbound.api.content.*;
import bikerdbmid.soulbound.components.content.debuffs.custom.*;
import net.minecraft.world.effect.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ModDebuffs {
    private static final Map<String, DeBuff> debuffMap = new HashMap<>();

    public static DeBuff SLOWNESS_LOW_1 = register(new EffectDebuff(EDistType.LOW, "slowness_low_1", MobEffects.SLOWNESS, 20, 0));
    public static DeBuff SLOWNESS_MEDIUM_2 = register(new EffectDebuff(EDistType.MEDIUM, "slowness_medium_2", MobEffects.SLOWNESS, 20, 1));
    public static DeBuff SLOWNESS_HIGH_3 = register(new EffectDebuff(EDistType.HIGH, "slowness_high_3", MobEffects.SLOWNESS, 20, 2));

    public static DeBuff SLOWNESS_LOW_2 = register(new EffectDebuff(EDistType.LOW, "slowness_low_2", MobEffects.SLOWNESS, 20, 1));
    public static DeBuff SLOWNESS_MEDIUM_3 = register(new EffectDebuff(EDistType.MEDIUM, "slowness_medium_3", MobEffects.SLOWNESS, 20, 2));
    public static DeBuff SLOWNESS_HIGH_4 = register(new EffectDebuff(EDistType.HIGH, "slowness_high_4", MobEffects.SLOWNESS, 20, 3));

    public static DeBuff HUNGER_LOW_1 = register(new EffectDebuff(EDistType.LOW, "hunger_low_1", MobEffects.HUNGER, 20, 0));
    public static DeBuff HUNGER_MEDIUM_2 = register(new EffectDebuff(EDistType.HIGH, "hunger_medium_2", MobEffects.HUNGER, 20, 1));

    public static DeBuff FREEZING = register(new FreezingDebuff(EDistType.HIGH, "freezing"));

    public static DeBuff SLOW_FALLING_1 = register(new EffectDebuff(EDistType.HIGH, "slow_falling", MobEffects.SLOW_FALLING, 20, 0));
    public static DeBuff POISON_1 = register(new EffectDebuff(EDistType.HIGH, "poison_1", MobEffects.POISON, 20, 0));

    public static DeBuff WITHER_1 = register(new EffectDebuff(EDistType.HIGH, "wither_1", MobEffects.WITHER, 20, 0));

    public static DeBuff BLINDESS_1_MEDIUM = register(new EffectDebuff(EDistType.MEDIUM, "blindness_1_medium", MobEffects.BLINDNESS, 20, 0));
    public static DeBuff BLINDESS_1_HIGH = register(new EffectDebuff(EDistType.HIGH, "blindness_1_high", MobEffects.BLINDNESS, 20, 0));

    public static DeBuff DROWNING_IN_AIR = register(new DeBuff(EDistType.MEDIUM, "drowning_in_air"));







    public static void init() {

    }

    private static DeBuff register(DeBuff debuff) {
        debuffMap.put(debuff.id, debuff);
        return debuff;
    }

    public static @Nullable DeBuff getDeBuff(String id) {
        return debuffMap.get(id);
    }

}
