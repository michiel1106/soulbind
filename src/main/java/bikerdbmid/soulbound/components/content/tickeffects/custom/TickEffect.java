package bikerdbmid.soulbound.components.content.tickeffects.custom;

import net.minecraft.nbt.*;
import net.minecraft.world.entity.player.*;

public abstract class TickEffect {
    public final String id;

    public TickEffect(String id) {
        this.id = id;
    }

    public void onApply(Player player, CompoundTag data) {}
    public boolean onUse(Player player, CompoundTag data) { return false; }
    public void tick(Player player, CompoundTag data) {}
    public void onRemove(Player player, CompoundTag data) {}
}
