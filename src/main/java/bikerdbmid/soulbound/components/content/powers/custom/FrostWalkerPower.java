package bikerdbmid.soulbound.components.content.powers.custom;

import bikerdbmid.soulbound.components.*;
import bikerdbmid.soulbound.components.Components.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class FrostWalkerPower extends Power {

    public FrostWalkerPower(String frostwalker) {
        super(frostwalker);
    }

    @Override
    public void tick(Player player, CompoundTag data) {
        super.tick(player, data);
        Level level = player.level();
        boolean frostwalker = data.getBooleanOr("frostwalker", false);

        if (SoulDataComponent.getActiveDebuffLevel(player).isPresent()) return;

        if (!frostwalker) return;

        int radius = 3;
        BlockPos center = player.blockPosition().below();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz > radius * radius) continue;

                pos.set(center.getX() + dx, center.getY(), center.getZ() + dz);
                BlockState state = level.getBlockState(pos);

                boolean waterSource = state.is(Blocks.WATER) && state.getValue(LiquidBlock.LEVEL) == 0;
                if (!waterSource || !level.getBlockState(pos.above()).isAir()) continue;

                BlockState ice = Blocks.FROSTED_ICE.defaultBlockState();
                if (ice.canSurvive(level, pos) && level.isUnobstructed(ice, pos, net.minecraft.world.phys.shapes.CollisionContext.empty())) {
                    level.setBlockAndUpdate(pos, ice);
                    level.scheduleTick(pos.immutable(), Blocks.FROSTED_ICE, Mth.nextInt(level.getRandom(), 60, 120));
                }
            }
        }

    }

    @Override
    public boolean onUse(Player player, CompoundTag data) {
        boolean frostwalker = data.getBooleanOr("frostwalker", true);
        data.putBoolean("frostwalker", !frostwalker);

        player.sendOverlayMessage(Component.literal("Toggled frostwalker to: " + (frostwalker ? "OFF" : "ON")));


        return super.onUse(player, data);
    }
}
