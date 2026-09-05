package bikerdbmid.soulbound.components.content.effect.custom;

import bikerdbmid.soulbound.components.IComponents.ISoulDataComponent;
import bikerdbmid.soulbound.components.content.buffs.custom.Buff;
import bikerdbmid.soulbound.components.content.debuffs.custom.DeBuff;
import bikerdbmid.soulbound.components.content.powers.custom.Power;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Effect {
    public final String id;
    private final List<Buff> buffs = new ArrayList<>();
    private final List<DeBuff> debuffs = new ArrayList<>();
    @Nullable private Power power;

    public Effect(String id) {
        this.id = id;
    }

    public Effect withBuff(Buff buff) { buffs.add(buff); return this; }
    public Effect withDebuff(DeBuff debuff) { debuffs.add(debuff); return this; }
    public Effect withPower(Power power) { this.power = power; return this; }

    public void apply(ISoulDataComponent component) {
        for (Buff b : buffs) component.addBuff(b.id);
        for (DeBuff d : debuffs) component.addDebuff(d.id);
        if (power != null) component.setPower(power.id);
    }

    public void remove(ISoulDataComponent component) {
        for (Buff b : buffs) component.removeBuff(b.id);
        for (DeBuff d : debuffs) component.removeDebuff(d.id);
        if (power != null && power.id.equals(component.getValue().power)) {
            component.setPower(null);
        }
    }
}