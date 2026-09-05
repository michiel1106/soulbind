package bikerdbmid.soulbound.commands;

import bikerdbmid.soulbound.components.*;
import bikerdbmid.soulbound.components.Components.*;
import bikerdbmid.soulbound.components.IComponents.*;
import bikerdbmid.soulbound.components.content.effect.*;
import bikerdbmid.soulbound.components.content.effect.custom.*;
import com.mojang.brigadier.arguments.*;
import net.fabricmc.fabric.api.command.v2.*;
import net.minecraft.commands.*;
import net.minecraft.commands.arguments.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.*;

public class ModCommands {

    public static void init() {

        CommandRegistrationCallback.EVENT.register(((dispatcher, commandBuildContext, commandSelection) -> {

            dispatcher.register(Commands.literal("soulbound")
                    .then(Commands.literal("effect").then(Commands.literal("set").then(Commands.argument("player", EntityArgument.player()).then(Commands.argument("effectid", StringArgumentType.string()).executes(ctx -> {
                        String id = StringArgumentType.getString(ctx, "effectid");
                        Player player = EntityArgument.getPlayer(ctx, "player");

                        ISoulDataComponent dataComponent = ModComponents.SOULDATA.get(player);

                        Effect effect = ModEffects.getEffect(id);

                        if (effect != null) {
                            dataComponent.setEffect(effect.id);
                        }

                        ctx.getSource().sendSuccess(() -> Component.literal("Set effect succesfully to: " + dataComponent.getValue().effect), false);

                        return 1;
                    })))))

                    .then(Commands.literal("effect").then(Commands.literal("clear").then(Commands.argument("player", EntityArgument.player()).executes(ctx -> {
                        Player player = EntityArgument.getPlayer(ctx, "player");
                        ISoulDataComponent dataComponent = ModComponents.SOULDATA.get(player);

                        dataComponent.setEffect("empty");

                        ctx.getSource().sendSuccess(() -> Component.literal("Successfully cleared effect!"), false);

                        return 1;
                    }))))

            );


        }));

    }

}
