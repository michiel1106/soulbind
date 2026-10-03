package bikerdbmid.soulbound.mixin.debuff;

import bikerdbmid.soulbound.components.*;
import bikerdbmid.soulbound.components.Components.*;
import bikerdbmid.soulbound.components.IComponents.*;
import bikerdbmid.soulbound.components.content.debuffs.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(Player.class)
public abstract class PlayerMixin extends Avatar {

    protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"))
    private void eventThing(Entity entity, CallbackInfo ci) {
        Player plr = (Player)(Object)this;

        ISoulDataComponent iSoulDataComponent = ModComponents.SOULDATA.get(plr);
        boolean debuffActive = iSoulDataComponent.isDebuffActive(ModDebuffs.REGEN_ON_SWORD_HIT);

        if (debuffActive) {
            if (random.nextInt(40) == 0) {
                if (entity instanceof Player player) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 0), null);
                }
            }
        }


    }

}
