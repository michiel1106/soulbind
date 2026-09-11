package bikerdbmid.soulbound.components.content.effect.custom;

import bikerdbmid.soulbound.api.content.*;
import bikerdbmid.soulbound.components.IComponents.ISoulDataComponent;
import bikerdbmid.soulbound.components.content.buffs.custom.Buff;
import bikerdbmid.soulbound.components.content.debuffs.custom.DeBuff;
import bikerdbmid.soulbound.components.content.powers.custom.Power;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class Effect {
    private @Nullable Block block;
    private Supplier<ItemStack> itemSupplier = () -> ItemStack.EMPTY;
    private @Nullable Identifier image;
    public ImgRenderType imgRenderType;
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

    public Effect withBlock(Block block) {this.block = block; this.imgRenderType = ImgRenderType.BLOCK; return this;}
    public Effect withIdentifier(Identifier identifier) {this.image = identifier; this.imgRenderType = ImgRenderType.IMAGE; return this;}
    public Effect withItem(Supplier<ItemStack> supplier) {this.itemSupplier = supplier; this.imgRenderType = ImgRenderType.ITEM; return this;}


    public @Nullable Identifier getImage() {
        return image;
    }

    public @Nullable Block getBlock() {
        return block;
    }

    public ItemStack getItem() {
        return itemSupplier.get();
    }

    public ImgRenderType getImgRenderType() {
        return imgRenderType;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public List<DeBuff> getDebuffs() {
        return debuffs;
    }

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