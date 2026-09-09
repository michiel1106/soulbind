package bikerdbmid.soulbound.components.content.effect;

import bikerdbmid.soulbound.*;
import bikerdbmid.soulbound.components.content.buffs.ModBuffs;
import bikerdbmid.soulbound.components.content.debuffs.ModDebuffs;
import bikerdbmid.soulbound.components.content.effect.custom.Effect;
import bikerdbmid.soulbound.components.content.powers.ModPowers;
import com.mojang.authlib.minecraft.client.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ModEffects {
    private static final Map<String, Effect> effectMap = new HashMap<>();

    public static Effect EMPTY = register(new Effect("empty"));


    public static Effect FEATHER = register(
            new Effect("feather")
                    .withItem(Items.FEATHER)
                    .withBuff(ModBuffs.JUMPING_JACK)
                    .withDebuff(ModDebuffs.LEVEL_1)
    );

    public static Effect POISON = register(
            new Effect("poison")
                    .withIdentifier(Identifier.fromNamespaceAndPath("minecraft", "textures/mob_effect/poison.png"))
    );

    public static Effect FROZEN = register(
            new Effect("frozen")
                    .withItem(Items.ICE)
    );

    public static Effect FIRE = register(
            new Effect("fire")
                    .withItem(Items.FLINT_AND_STEEL)
    );

    public static Effect INVISIBILITY = register(
            new Effect("invisibility")
                    .withIdentifier(Identifier.fromNamespaceAndPath("minecraft", "textures/mob_effect/invisibility.png"))
    );





    public static void init() {}

    private static Effect register(Effect effect) {
        effectMap.put(effect.id, effect);
        return effect;
    }

    public static List<Effect> getAllEffectsExceptEmpty() {
        var list = new ArrayList<Effect>();
        for (Map.Entry<String, Effect> stringEffectEntry : effectMap.entrySet()) {
            list.add(stringEffectEntry.getValue());
        }

        list.remove(EMPTY);

        return list;
    }

    public static @Nullable Effect getEffect(String id) {
        return effectMap.get(id);
    }
}