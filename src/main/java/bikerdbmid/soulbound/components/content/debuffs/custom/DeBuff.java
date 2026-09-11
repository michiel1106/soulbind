package bikerdbmid.soulbound.components.content.debuffs.custom;

import bikerdbmid.soulbound.api.content.*;
import net.minecraft.nbt.*;
import net.minecraft.world.entity.player.*;

public class DeBuff {
    public final String id;
    public final EDistType distType;

    public DeBuff(EDistType distType, String id) {
        this.distType = distType;
        this.id = id;
    }

    public void onApply(Player player, CompoundTag data) {}
    public void tick(Player player, CompoundTag data) {}
    public void onRemove(Player player, CompoundTag data) {}
}
