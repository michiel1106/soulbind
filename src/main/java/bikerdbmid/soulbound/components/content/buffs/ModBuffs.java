package bikerdbmid.soulbound.components.content.buffs;

import bikerdbmid.soulbound.api.content.*;
import bikerdbmid.soulbound.components.content.buffs.custom.*;
import net.minecraft.world.effect.*;
import org.jspecify.annotations.*;

import java.util.*;

public class ModBuffs {
    private static final Map<String, Buff> buffMap = new HashMap<>();

    public static Buff JUMPING_JACK = register(new EffectBuff(EDistType.HIGH, "jumping_jack", MobEffects.JUMP_BOOST, 20, 1));







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
