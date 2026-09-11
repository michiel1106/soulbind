package bikerdbmid.soulbound.components.content.buffs;

import bikerdbmid.soulbound.api.content.*;
import bikerdbmid.soulbound.components.content.buffs.custom.*;
import net.minecraft.world.effect.*;
import org.jspecify.annotations.*;

import java.util.*;

public class ModBuffs {
    private static final Map<String, Buff> buffMap = new HashMap<>();

    public static Buff JUMPING_JACK = register(new EffectBuff("jumping_jack", MobEffects.JUMP_BOOST, 20, 1));

    public static Buff SWIFTNESS_2 = register(new EffectBuff("swiftness_2", MobEffects.SPEED, 20, 1));
    public static Buff JUMP_BOOST_2 = register(new EffectBuff("jump_boost_2", MobEffects.JUMP_BOOST, 20, 1));
    public static Buff REGENERATION_1 = register(new EffectBuff("regeneration_1", MobEffects.REGENERATION, 20, 0));
    public static Buff RESISTANCE_1 = register(new EffectBuff("resistance_1", MobEffects.RESISTANCE, 20, 0));
    public static Buff NIGHT_VISION_1 = register(new EffectBuff("night_vision_1", MobEffects.NIGHT_VISION, 20, 0));
    public static Buff SATURATION_1 = register(new EffectBuff("saturation_1", MobEffects.SATURATION, 20, 0));
    public static Buff FIRE_RESISTANCE_1 = register(new EffectBuff("fire_resistance_1", MobEffects.FIRE_RESISTANCE, 20, 0));
    public static Buff DOLPHINS_GRACE_1 = register(new EffectBuff("dolphins_grace_1", MobEffects.DOLPHINS_GRACE, 20, 0));
    public static Buff HASTE_1 = register(new EffectBuff("haste_1", MobEffects.HASTE, 20, 0));

    public static Buff PLENTIFUL_CROPS = register(new Buff("plentiful_crops"));
    public static Buff CLEAR_WEATHER_3x3_CHUNKS = register(new Buff("clear_weather_3x3_chunks"));
    public static Buff MOBS_IGNORE_RAIN = register(new Buff("mobs_ignore_rain"));
    public static Buff PROJECTILES_CANT_HIT = register(new Buff("projectiles_cant_hit"));
    public static Buff MOBS_CLOSER_FOR_AGRESSION = register(new Buff("mobs_closer_for_agression"));
    public static Buff ONE_POINT_FIVE_MOB_DROPS = register(new Buff("one_point_five_mob_drops"));






    public static void init() {

    }

    private static Buff register(Buff buff) {
        buffMap.put(buff.id, buff);
        return buff;
    }

    public static @Nullable Buff getBuff(String id) {
        return buffMap.get(id);
    }

}
