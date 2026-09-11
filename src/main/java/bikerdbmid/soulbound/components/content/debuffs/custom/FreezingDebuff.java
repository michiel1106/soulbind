package bikerdbmid.soulbound.components.content.debuffs.custom;

import bikerdbmid.soulbound.api.content.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;

import java.lang.reflect.*;

public class FreezingDebuff extends DeBuff{

    public FreezingDebuff(EDistType distType, String id) {
        super(distType, id);
    }

    @Override
    public void tick(Player player, CompoundTag data) {
        super.tick(player, data);

        Entity entity = player;
        InsideBlockEffectApplier insideBlockEffectApplier = null;

        try {
            Field insideEffectCollector = Entity.class.getDeclaredField("insideEffectCollector");
            insideEffectCollector.setAccessible(true);
            insideBlockEffectApplier = (InsideBlockEffectApplier) insideEffectCollector.get(player);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            player.sendOverlayMessage(Component.literal("Freezing debuff failed to execute. Check console."));
            e.printStackTrace();
        }

        if (insideBlockEffectApplier != null) {
            insideBlockEffectApplier.apply(InsideBlockEffectType.FREEZE);
            insideBlockEffectApplier.apply(InsideBlockEffectType.EXTINGUISH);
        }


    }
}
