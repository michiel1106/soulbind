package bikerdbmid.soulbound.client.screen;

import bikerdbmid.soulbound.client.*;
import net.fabricmc.fabric.api.client.rendering.v1.hud.*;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

public class CooldownBarHud {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 5;

    public static void register() {
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.HOTBAR,
                Identifier.fromNamespaceAndPath("soulbound", "cooldown_bar"),
                CooldownBarHud::extract
        );
    }

    private static void extract(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (!ClientCooldownState.active) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.gui.hud.isHidden()) return;

        float partial = deltaTracker.getGameTimeDeltaPartialTick(false);
        float remaining = Math.max(0f, ClientCooldownState.remaining - partial);
        float fraction = remaining / ClientCooldownState.total;

        int x = (mc.getWindow().getGuiScaledWidth() - WIDTH) / 2;
        int y = mc.getWindow().getGuiScaledHeight() - 50;

        graphics.fill(x - 1, y - 1, x + WIDTH + 1, y + HEIGHT + 1, 0xFF000000);
        graphics.fill(x, y, x + WIDTH, y + HEIGHT, 0xFF3A3A3A);
        graphics.fill(x, y, x + (int) (WIDTH * fraction), y + HEIGHT, 0xFF55AAFF);
    }
}
