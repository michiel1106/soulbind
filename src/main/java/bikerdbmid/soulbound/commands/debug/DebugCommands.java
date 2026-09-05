package bikerdbmid.soulbound.commands.debug;

import bikerdbmid.soulbound.components.*;
import bikerdbmid.soulbound.components.IComponents.*;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.context.*;
import com.mojang.brigadier.exceptions.*;
import net.fabricmc.fabric.api.command.v2.*;
import net.fabricmc.loader.api.*;
import net.minecraft.commands.*;
import net.minecraft.commands.arguments.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;

public class DebugCommands {

    public static void init() {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment()) return;

        CommandRegistrationCallback.EVENT.register(((dispatcher, commandBuildContext, commandSelection) -> {

            dispatcher.register(Commands.literal("soulbound").then(Commands.literal("debug").then(Commands.literal("component")

                    .then(Commands.literal("add").then(Commands.argument("player", EntityArgument.player())
                                    .then(Commands.literal("buffs").then(Commands.argument("id", StringArgumentType.string())
                                            .executes(ctx -> withComponent(ctx, (c, id) -> c.addBuff(id)))))
                            .then(Commands.literal("debuffs").then(Commands.argument("id", StringArgumentType.string())
                                    .executes(ctx -> withComponent(ctx, (c, id) -> c.addDebuff(id)))))
                    ))

                    .then(Commands.literal("remove").then(Commands.argument("player", EntityArgument.player())
                                    .then(Commands.literal("buffs").then(Commands.argument("id", StringArgumentType.string())
                                            .executes(ctx -> withComponent(ctx, (c, id) -> c.removeBuff(id)))))
                            .then(Commands.literal("debuffs").then(Commands.argument("id", StringArgumentType.string())
                                    .executes(ctx -> withComponent(ctx, (c, id) -> c.removeDebuff(id)))))
                    ))

                    .then(Commands.literal("power").then(Commands.argument("player", EntityArgument.player())
                            .then(Commands.literal("set").then(Commands.argument("id", StringArgumentType.string())
                                    .executes(ctx -> withComponent(ctx, (c, id) -> c.setPower(id)))))
                            .then(Commands.literal("clear").executes(ctx -> withComponent(ctx, "id", (c, id) -> c.setPower(null))))
                    ))

                    .then(Commands.literal("set").then(Commands.argument("player", EntityArgument.player())
                            .then(Commands.literal("uuid").then(Commands.argument("uuid", UuidArgument.uuid())
                                    .executes(DebugCommands::setUUID)))))

                    .then(Commands.literal("get").then(Commands.argument("player", EntityArgument.player()).executes(ctx -> {
                        ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
                        ISoulDataComponent.SoulData value = ModComponents.SOULDATA.get(player).getValue();

                        StringBuilder buffstr = new StringBuilder();
                        StringBuilder debuffstr = new StringBuilder();

                        value.buffs.forEach((id, data) -> buffstr.append(id).append(" -> ").append(data).append("\n"));
                        value.debuffs.forEach((id, data) -> debuffstr.append(id).append(" -> ").append(data).append("\n"));

                        ctx.getSource().sendSystemMessage(Component.literal("Buffs: " + buffstr));
                        ctx.getSource().sendSystemMessage(Component.literal("Debuffs: " + debuffstr));
                        ctx.getSource().sendSystemMessage(Component.literal("Power: " + value.power + " " + value.powerData));
                        ctx.getSource().sendSystemMessage(Component.literal("uuid: " + value.uuid));
                        ctx.getSource().sendSystemMessage(Component.literal("----------------------------------------------------"));

                        return 0;
                    })))
            )));

        }));
    }

    private static int setUUID(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        ModComponents.SOULDATA.get(player).getValue().setUuid(UuidArgument.getUuid(context, "uuid"));
        return 0;
    }

    @FunctionalInterface
    private interface ComponentAction {
        void apply(ISoulDataComponent component, String id);
    }

    private static int withComponent(CommandContext<CommandSourceStack> context, ComponentAction action) throws CommandSyntaxException {
        return withComponent(context, "id", action);
    }

    private static int withComponent(CommandContext<CommandSourceStack> context, String argName, ComponentAction action) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        String id = argExistsOrEmpty(context, argName);
        action.apply(ModComponents.SOULDATA.get(player), id);
        return 0;
    }

    private static String argExistsOrEmpty(CommandContext<CommandSourceStack> context, String argName) {
        try {
            return StringArgumentType.getString(context, argName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}