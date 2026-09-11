package bikerdbmid.soulbound.mixin.debuff;

import bikerdbmid.soulbound.components.*;
import bikerdbmid.soulbound.components.IComponents.*;
import bikerdbmid.soulbound.components.content.debuffs.*;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.tags.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @WrapOperation(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean makePlayerDrown(LivingEntity entity, TagKey<?> tagKey, Operation<Boolean> original) {
        if (entity instanceof Player player) {
            ISoulDataComponent soulDataComponent = ModComponents.SOULDATA.get(player);

            if (soulDataComponent.isDebuffActive(ModDebuffs.DROWNING_IN_AIR)) {
                return !original.call(entity, tagKey);
            }
        }

        return original.call(entity, tagKey);
    }

}
