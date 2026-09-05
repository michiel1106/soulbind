package bikerdbmid.soulbound.components.Components;

import bikerdbmid.soulbound.api.content.*;
import bikerdbmid.soulbound.components.IComponents.*;
import bikerdbmid.soulbound.components.ModComponents;
import bikerdbmid.soulbound.components.content.buffs.ModBuffs;
import bikerdbmid.soulbound.components.content.buffs.custom.Buff;
import bikerdbmid.soulbound.components.content.debuffs.ModDebuffs;
import bikerdbmid.soulbound.components.content.debuffs.custom.DeBuff;
import bikerdbmid.soulbound.components.content.effect.*;
import bikerdbmid.soulbound.components.content.effect.custom.*;
import bikerdbmid.soulbound.components.content.powers.ModPowers;
import bikerdbmid.soulbound.components.content.powers.custom.Power;
import com.mojang.serialization.Codec;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.*;

public class SoulDataComponent implements ISoulDataComponent, AutoSyncedComponent, CommonTickingComponent {
    private final SoulData soulData = new SoulData();
    private final Player player;

    public SoulDataComponent(Player player) {
        this.player = player;
    }

    @Override
    public SoulData getValue() {
        return soulData;
    }

    @Override
    public void addBuff(String id) {
        if (soulData.buffs.containsKey(id)) return; // already active
        Buff buff = ModBuffs.getBuff(id);
        if (buff == null) return; // unknown id, ignore
        CompoundTag data = new CompoundTag();
        buff.onApply(player, data);
        soulData.buffs.put(id, data);
        sync();
    }

    @Override
    public void removeBuff(String id) {
        CompoundTag data = soulData.buffs.remove(id);
        if (data == null) return;
        Buff buff = ModBuffs.getBuff(id);
        if (buff != null) buff.onRemove(player, data);
        sync();
    }

    @Override
    public void addDebuff(String id) {
        if (soulData.debuffs.containsKey(id)) return;
        DeBuff debuff = ModDebuffs.getDeBuff(id);
        if (debuff == null) return;
        CompoundTag data = new CompoundTag();
        debuff.onApply(player, data);
        soulData.debuffs.put(id, data);
        sync();
    }

    @Override
    public void removeDebuff(String id) {
        CompoundTag data = soulData.debuffs.remove(id);
        if (data == null) return;
        DeBuff debuff = ModDebuffs.getDeBuff(id);
        if (debuff != null) debuff.onRemove(player, data);
        sync();
    }


    @Override
    public void setPower(@Nullable String id) {
        if (soulData.power != null) {
            Power old = ModPowers.getPower(soulData.power);
            if (old != null) old.onRemove(player, soulData.powerData);
        }

        soulData.powerData = new CompoundTag();
        soulData.power = id;

        if (id != null) {
            Power fresh = ModPowers.getPower(id);
            if (fresh != null) fresh.onApply(player, soulData.powerData);
        }
        sync();
    }

    @Override
    public void setEffect(@Nullable String id) {
        if (soulData.effect != null) {
            Effect old = ModEffects.getEffect(soulData.effect);
            if (old != null) old.remove(this);
        }

        soulData.effect = id;

        if (id != null) {
            Effect fresh = ModEffects.getEffect(id);
            if (fresh != null) fresh.apply(this);
        }
        sync();
    }

    private void sync() {
        ModComponents.SOULDATA.sync(player);
    }

    @Override
    public void readData(ValueInput valueInput) {
        valueInput.read("uuid", UUIDUtil.CODEC).ifPresent(soulData::setUuid);
        valueInput.read("buffs", Codec.unboundedMap(Codec.STRING, CompoundTag.CODEC))
                .ifPresent(m -> { soulData.buffs.clear(); soulData.buffs.putAll(m); });
        valueInput.read("debuffs", Codec.unboundedMap(Codec.STRING, CompoundTag.CODEC))
                .ifPresent(m -> { soulData.debuffs.clear(); soulData.debuffs.putAll(m); });

        // absence now explicitly means "no power" - don't just skip
        soulData.power = valueInput.read("power", Codec.STRING).orElse(null);
        soulData.powerData = valueInput.read("powerData", CompoundTag.CODEC).orElse(new CompoundTag());

        soulData.effect = valueInput.read("effect", Codec.STRING).orElse(null);
    }

    @Override
    public void writeData(ValueOutput valueOutput) {
        if (soulData.uuid != null) {
            valueOutput.store("uuid", UUIDUtil.CODEC, soulData.uuid);
        }
        valueOutput.store("buffs", Codec.unboundedMap(Codec.STRING, CompoundTag.CODEC), soulData.buffs);
        valueOutput.store("debuffs", Codec.unboundedMap(Codec.STRING, CompoundTag.CODEC), soulData.debuffs);
        if (soulData.power != null) {
            valueOutput.store("power", Codec.STRING, soulData.power);
        }
        valueOutput.store("powerData", CompoundTag.CODEC, soulData.powerData);
        if (soulData.effect != null) {
            valueOutput.store("effect", Codec.STRING, soulData.effect);
        }
    }

    @Override
    public void tick() {
        if (player.level().isClientSide()) return; // gameplay logic is server-authoritative

        Optional<EDistType> activeDebuffLevel = getActiveDebuffLevel();
        boolean buffsAndPowerAllowed = activeDebuffLevel.isEmpty();

        for (Map.Entry<String, CompoundTag> e : soulData.buffs.entrySet()) {
            if (!buffsAndPowerAllowed) continue; // any active debuff bracket suppresses all buffs
            Buff buff = ModBuffs.getBuff(e.getKey());
            if (buff != null) buff.tick(player, e.getValue());
        }

        for (Map.Entry<String, CompoundTag> e : soulData.debuffs.entrySet()) {
            DeBuff debuff = ModDebuffs.getDeBuff(e.getKey());
            if (debuff == null) continue;
            if (activeDebuffLevel.isPresent() && debuff.distType == activeDebuffLevel.get()) {
                debuff.tick(player, e.getValue());
            }
        }

        if (soulData.power != null) {
            Power power = ModPowers.getPower(soulData.power);
            // cooldowns etc. still decay regardless of gating - only *activation* is blocked, see activatePower()
            if (power != null) power.tick(player, soulData.powerData);
        }
    }

    @Override
    public boolean activatePower() {
        if (soulData.power == null) return false;
        if (getActiveDebuffLevel().isPresent()) return false; // too far from soulmate to use power

        Power power = ModPowers.getPower(soulData.power);
        if (power == null) return false;

        boolean used = power.onUse(player, soulData.powerData);
        if (used) sync();
        return used;
    }

    /**
     * Empty = no partner set, OR partner close enough that no debuff bracket applies.
     * Present = the debuff severity that should currently be active (buffs/power suppressed).
     */
    private Optional<EDistType> getActiveDebuffLevel() {
        UUID uuid = soulData.uuid;
        if (uuid == null) return Optional.empty(); // no partner - mechanic doesn't apply

        if (!(player.level() instanceof ServerLevel serverLevel)) return Optional.empty();

        // check this dimension first - cheap, and covers the common case
        Player partner = serverLevel.getPlayerByUUID(uuid);
        if (partner != null) {
            double distance = player.distanceTo(partner);
            return EDistType.forDistance(distance);
        }

        // not in this dimension - are they online elsewhere, or fully offline?
        ServerPlayer partnerAnywhere = serverLevel.getServer().getPlayerList().getPlayer(uuid);
        if (partnerAnywhere == null) {
            return Optional.of(EDistType.LOW); // offline
        }

        return Optional.of(EDistType.HIGH); // online, different dimension - definitely far
    }
}