package bikerdbmid.soulbound.components.content.effect;

import bikerdbmid.soulbound.components.content.buffs.ModBuffs;
import bikerdbmid.soulbound.components.content.debuffs.ModDebuffs;
import bikerdbmid.soulbound.components.content.effect.custom.Effect;
import bikerdbmid.soulbound.components.content.powers.ModPowers;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ModEffects {
    private static final Map<String, Effect> effectMap = new HashMap<>();

    public static Effect EMPTY = register(new Effect("empty"));

    public static Effect FROZEN_CURSE = register(
            new Effect("frozen_curse")
                    .withBuff(ModBuffs.JUMPING_JACK)
                    .withDebuff(ModDebuffs.SLOWNESS)
                    .withPower(ModPowers.ICE_ARMOR)
    );

    public static void init() {}

    private static Effect register(Effect effect) {
        effectMap.put(effect.id, effect);
        return effect;
    }

    public static @Nullable Effect getEffect(String id) {
        return effectMap.get(id);
    }
}