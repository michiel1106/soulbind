package bikerdbmid.soulbound.components.content.debuffs.custom;

import bikerdbmid.soulbound.api.content.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.*;

public class MessageDebuff extends DeBuff {
    String message;

    public MessageDebuff(EDistType distType, String id, String message) {
        super(distType, id);
        this.message = message;
    }

    @Override
    public void tick(Player player, CompoundTag data) {
        super.tick(player, data);
        player.sendOverlayMessage(Component.literal(message));


    }
}
