package bikerdbmid.soulbound.components.IComponents;

import bikerdbmid.soulbound.components.content.buffs.custom.*;
import bikerdbmid.soulbound.components.content.debuffs.custom.*;
import net.minecraft.nbt.*;
import org.jspecify.annotations.*;
import org.ladysnake.cca.api.v3.component.*;
import org.spongepowered.asm.mixin.*;

import java.util.*;

public interface ISoulDataComponent extends Component {
    SoulData getValue();

    void addBuff(String id);
    void removeBuff(String id);
    void addDebuff(String id);
    void removeDebuff(String id);
    void setPower(@Nullable String id);
    boolean activatePower();
    void setEffect(@Nullable String id);
    boolean isDebuffActive(DeBuff deBuff);
    boolean isBuffActive(Buff buff);


    class SoulData {
        @Nullable public UUID uuid;
        public final Map<String, CompoundTag> buffs = new HashMap<>();
        public final Map<String, CompoundTag> debuffs = new HashMap<>();
        @Nullable public String power;
        public CompoundTag powerData = new CompoundTag();
        @Nullable public String effect; // currently applied effect id, if any

        public void setUuid(@Nullable UUID uuid) { this.uuid = uuid; }
    }
}
