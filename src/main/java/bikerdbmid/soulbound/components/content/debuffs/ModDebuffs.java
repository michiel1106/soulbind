package bikerdbmid.soulbound.components.content.debuffs;

import bikerdbmid.soulbound.api.content.*;
import bikerdbmid.soulbound.components.content.debuffs.custom.*;
import net.minecraft.world.effect.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ModDebuffs {
    private static final Map<String, DeBuff> debuffMap = new HashMap<>();

    public static DeBuff SLOWNESS = register(new EffectDebuff(EDistType.HIGH, "slowness", MobEffects.SLOWNESS, 20, 1));







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
