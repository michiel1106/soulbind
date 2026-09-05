package bikerdbmid.soulbound.components;

import bikerdbmid.soulbound.*;
import bikerdbmid.soulbound.components.Components.*;
import bikerdbmid.soulbound.components.IComponents.*;
import net.minecraft.world.entity.player.*;
import org.ladysnake.cca.api.v3.component.*;
import org.ladysnake.cca.api.v3.entity.*;

public class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<ISoulDataComponent> SOULDATA = ComponentRegistry.getOrCreate(SoulBound.id("souldata"), ISoulDataComponent.class);


    public static void init() {

    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(Player.class, SOULDATA).impl(SoulDataComponent.class).end(SoulDataComponent::new);
    }
}
