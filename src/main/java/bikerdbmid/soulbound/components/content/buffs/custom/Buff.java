package bikerdbmid.soulbound.components.content.buffs.custom;

import bikerdbmid.soulbound.api.content.*;
import net.minecraft.nbt.*;
import net.minecraft.world.entity.player.*;

public class Buff {
    public final String id;

    public Buff(String id) {
        this.id = id;
    }

    public void onApply(Player player, CompoundTag data) {}
    public void tick(Player player, CompoundTag data) {}
    public void onRemove(Player player, CompoundTag data) {}
}
