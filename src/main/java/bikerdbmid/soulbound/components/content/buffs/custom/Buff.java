package bikerdbmid.soulbound.components.content.buffs.custom;

import bikerdbmid.soulbound.api.content.*;
import net.minecraft.world.entity.player.*;

public abstract class Buff {
    EDistType eDistType;
    String id;

    public Buff(EDistType eDistType, String id) {
        this.eDistType = eDistType;
        this.id = id;
    }


    public abstract void tick(Player player);


}
