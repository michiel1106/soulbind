package bikerdbmid.soulbound.components.Components;

import bikerdbmid.soulbound.components.IComponents.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.storage.*;
import org.ladysnake.cca.api.v3.component.sync.*;
import org.ladysnake.cca.api.v3.component.tick.*;

import java.util.*;

public class SoulDataComponent implements ISoulDataComponent, AutoSyncedComponent, CommonTickingComponent {
    private SoulData soulData = new SoulData(null, new ArrayList<>(), new ArrayList<>());
    private final Player player;


    public SoulDataComponent(Player player) {
        this.player = player;
    }

    @Override
    public SoulData getValue() {
        return soulData;
    }

    @Override
    public void readData(ValueInput valueInput) {
        Optional<List<String>> buffs = valueInput.read("buffs", Codec.list(Codec.STRING));
        Optional<List<String>> debuffs = valueInput.read("debuffs", Codec.list(Codec.STRING));
        Optional<UUID> uuid = valueInput.read("uuid", UUIDUtil.CODEC);

        buffs.ifPresent(soulData::setBuffs);
        debuffs.ifPresent(soulData::setDebuffs);
        uuid.ifPresent(soulData::setUuid);
    }

    @Override
    public void writeData(ValueOutput valueOutput) {
        valueOutput.store("buffs", Codec.list(Codec.STRING), soulData.buffs);
        valueOutput.store("debuffs", Codec.list(Codec.STRING), soulData.debuffs);
        if (soulData.uuid != null) {
            valueOutput.store("uuid", UUIDUtil.CODEC, soulData.uuid);
        }
    }

    @Override
    public void tick() {

    }
}
