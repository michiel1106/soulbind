package bikerdbmid.soulbound.components.content.buffs.custom;

import net.minecraft.nbt.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;

public class Absorption_Two_Hearts extends Buff {


    public Absorption_Two_Hearts(String id) {
        super(id);
    }

    @Override
    public void tick(Player player, CompoundTag data) {
        super.tick(player, data);

        if (player.getAbsorptionAmount() < 4.0f) {
            int tickTime = data.getIntOr("tickTime", 3600);
            if (tickTime > 0) {
                tickTime--;
                data.putInt("tickTime", tickTime);
            } else if (tickTime == 0) {
                data.putInt("tickTime", 3600);
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1000000000, 0, true, false));
            }

        }


    }

    @Override
    public void onApply(Player player, CompoundTag data) {
        super.onApply(player, data);
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1000000000, 0, true, false));
    }

    @Override
    public void onRemove(Player player, CompoundTag data) {
        super.onRemove(player, data);
        player.removeEffect(MobEffects.ABSORPTION);
    }
}
