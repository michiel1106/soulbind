package bikerdbmid.soulbound.components.content.tickeffects;

import bikerdbmid.soulbound.components.content.tickeffects.custom.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ModTickEffects {
    private static final Map<String, TickEffect> tickEffectMap = new HashMap<>();



    public static void init() {}

    private static TickEffect register(TickEffect power) {
        tickEffectMap.put(power.id, power);
        return power;
    }

    public static @Nullable TickEffect getTickEffect(String id) {
        return tickEffectMap.get(id);
    }
}
