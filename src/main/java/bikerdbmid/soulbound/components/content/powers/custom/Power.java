package bikerdbmid.soulbound.components.content.powers.custom;

import bikerdbmid.soulbound.api.content.*;
import net.minecraft.nbt.*;
import net.minecraft.world.entity.player.*;

public abstract class Power {
    public final String id;
    public final EDistType distType;

    protected Power(EDistType distType, String id) {
        this.distType = distType;
        this.id = id;
    }

    public void onApply(Player player, CompoundTag data) {}
    public boolean onUse(Player player, CompoundTag data) { return false; }
    public void tick(Player player, CompoundTag data) {}
    public void onRemove(Player player, CompoundTag data) {}
}
