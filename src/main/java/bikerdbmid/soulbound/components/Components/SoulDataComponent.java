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
    private SoulData soulData = new SoulData(null, "", "");
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
        Optional<UUID> uuid = valueInput.read("uuid", UUIDUtil.CODEC);
        Optional<String> ability = valueInput.read("ability", Codec.STRING);
        Optional<String> effect = valueInput.read("effect", Codec.STRING);

        uuid.ifPresent(soulData::setUuid);
        ability.ifPresent(soulData::setAbility);
        effect.ifPresent(soulData::setEffect);

    }

    @Override
    public void writeData(ValueOutput valueOutput) {
        if (soulData.uuid != null) {
            valueOutput.store("uuid", UUIDUtil.CODEC, soulData.uuid);
        }
        valueOutput.store("ability", Codec.STRING, soulData.ability);
        valueOutput.store("effect", Codec.STRING, soulData.effect);
    }

    @Override
    public void tick() {

    }
}
