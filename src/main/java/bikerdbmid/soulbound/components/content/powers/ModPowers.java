package bikerdbmid.soulbound.components.content.powers;

import bikerdbmid.soulbound.api.content.EDistType;
import bikerdbmid.soulbound.components.content.powers.custom.*;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ModPowers {
    private static final Map<String, Power> powerMap = new HashMap<>();

    public static Power ICE_ARMOR = register(new IceArmorPower(EDistType.HIGH, "ice_armor", 40));

    public static void init() {}

    private static Power register(Power power) {
        powerMap.put(power.id, power);
        return power;
    }

    public static @Nullable Power getPower(String id) {
        return powerMap.get(id);
    }
}