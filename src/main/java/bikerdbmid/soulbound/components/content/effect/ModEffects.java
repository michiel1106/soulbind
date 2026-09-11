package bikerdbmid.soulbound.components.content.effect;

import bikerdbmid.soulbound.*;
import bikerdbmid.soulbound.components.content.buffs.ModBuffs;
import bikerdbmid.soulbound.components.content.debuffs.ModDebuffs;
import bikerdbmid.soulbound.components.content.effect.custom.Effect;
import bikerdbmid.soulbound.components.content.powers.ModPowers;
import com.mojang.authlib.minecraft.client.*;
import net.minecraft.core.component.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ModEffects {
    private static final Map<String, Effect> effectMap = new HashMap<>();

    public static Effect EMPTY = register(new Effect("empty"));


    public static Effect FEATHER = register(
            new Effect("feather")
                    .withItem(Items.FEATHER::getDefaultInstance)
                    .withBuff(ModBuffs.JUMP_BOOST_2)
    );

    public static Effect POISON = register(
            new Effect("poison")
                    .withIdentifier(Identifier.fromNamespaceAndPath("minecraft", "textures/mob_effect/poison"))
    );

    public static Effect FROZEN = register(
            new Effect("frozen")
                    .withIdentifier(SoulBound.id("textures/effect/icon/frozen"))
    );

    public static Effect GROWTH = register(
            new Effect("growth")
                    .withIdentifier(SoulBound.id("textures/effect/icon/growth"))
    );

    public static Effect FIRE = register(
            new Effect("fire")
                    .withIdentifier(SoulBound.id("textures/effect/icon/fire"))
    );

    public static Effect THUNDER = register(
            new Effect("thunder")
                    .withIdentifier(SoulBound.id("textures/effect/icon/thunder"))
    );

    public static Effect WATER = register(
            new Effect("water")
                    .withItem(Items.WATER_BUCKET::getDefaultInstance)
                    .withDebuff(ModDebuffs.DROWNING_IN_AIR)
                    .withBuff(ModBuffs.DOLPHINS_GRACE_1)
                    .withBuff(ModBuffs.RESISTANCE_1)
                    .withBuff(ModBuffs.PROJECTILES_CANT_HIT)

    );

    public static Effect NECROMANCY = register(
            new Effect("necromancy")
                    .withIdentifier(SoulBound.id("textures/effect/icon/necromancy"))
    );

    public static Effect INVISIBILITY = register(
            new Effect("invisibility")
                    .withItem(ModEffects::getPotionItem)
                    .withIdentifier(Identifier.fromNamespaceAndPath("minecraft", "textures/mob_effect/invisibility.png"))
    );

    public static Effect SWAP = register(
            new Effect("swap")
                    .withIdentifier(SoulBound.id("textures/effect/icon/swap"))
    );




    private static ItemStack getPotionItem() {
        PotionItem item = new PotionItem(new Item.Properties().component(DataComponents.POTION_CONTENTS, new PotionContents(Potions.INVISIBILITY)));
        return new ItemStack(item);
    }


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