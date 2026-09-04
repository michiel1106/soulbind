package bikerdbmid.soulbound.debug;

import bikerdbmid.soulbound.components.*;
import bikerdbmid.soulbound.components.IComponents.*;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.*;
import com.mojang.brigadier.context.*;
import com.mojang.brigadier.exceptions.*;
import net.fabricmc.fabric.api.command.v2.*;
import net.fabricmc.loader.api.*;
import net.minecraft.commands.*;
import net.minecraft.commands.arguments.*;
import net.minecraft.server.level.*;

import java.util.*;

public class DebugCommands {

    public static void init() {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment()) return;

        CommandRegistrationCallback.EVENT.register(((dispatcher, commandBuildContext, commandSelection) -> {
            LiteralArgumentBuilder<CommandSourceStack> base = Commands.literal("soulbound").then(Commands.literal("debug"));

            dispatcher.register(base.then(Commands.literal("component")
                    .then(Commands.literal("add").then(Commands.argument("player", EntityArgument.player())
                            .then(Commands.literal("buffs").then(Commands.argument("id", StringArgumentType.string()).executes((commandContext -> addToList(commandContext, "buffs", "id")))))
                            .then(Commands.literal("debuffs").then(Commands.argument("id", StringArgumentType.string()).executes((commandContext -> addToList(commandContext, "debuffs", "id")))))
                    ))

                    .then(Commands.literal("remove").then(Commands.argument("player", EntityArgument.player())
                            .then(Commands.literal("buffs").then(Commands.argument("id", StringArgumentType.string()).executes((commandContext -> removeFromList(commandContext, "buffs", "id")))))
                            .then(Commands.literal("debuffs").then(Commands.argument("id", StringArgumentType.string()).executes((commandContext -> removeFromList(commandContext, "debuffs", "id")))))
                    ))

                    .then(Commands.literal("set").then(Commands.argument("player", EntityArgument.player())
                            .then(Commands.literal("uuid").then(Commands.argument("uuid", UuidArgument.uuid()).executes((DebugCommands::setUUID))))))

            ));

        }));

    }

    private static int setUUID(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        ISoulDataComponent.SoulData value = ModComponents.SOULDATA.get(player).getValue();
        value.uuid = UuidArgument.getUuid(context, "uuid");

        return 0;
    }

    private static int addToList(CommandContext<CommandSourceStack> context, String listId, String id) throws CommandSyntaxException {
        String string = StringArgumentType.getString(context, id);
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        ISoulDataComponent.SoulData value = ModComponents.SOULDATA.get(player).getValue();

        switch (listId) {
            case  "buffs":
                value.buffs.add(string);
            case "debuffs" :
                value.debuffs.add(string);
        }

        return 0;
    }

    private static int removeFromList(CommandContext<CommandSourceStack> context, String listId, String id) throws CommandSyntaxException {
        String string = StringArgumentType.getString(context, id);
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        ISoulDataComponent.SoulData value = ModComponents.SOULDATA.get(player).getValue();

        switch (listId) {
            case  "buffs":
                value.buffs.remove(string);
            case "debuffs" :
                value.debuffs.remove(string);
        }

        return 0;
    }

}
