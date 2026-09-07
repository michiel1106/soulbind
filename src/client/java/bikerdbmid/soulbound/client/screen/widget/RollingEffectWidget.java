package bikerdbmid.soulbound.client.screen.widget;

import bikerdbmid.soulbound.*;
import bikerdbmid.soulbound.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.narration.*;
import net.minecraft.client.input.*;
import net.minecraft.client.renderer.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;

import java.util.*;

public class RollingEffectWidget extends AbstractWidget {
    private static Random random = new Random();

    private static final int ITEM_SIZE = 100;
    private static final int ITEM_GAP = 10;
    private static final int ITEM_HEIGHT = ITEM_SIZE + ITEM_GAP; // 110

    List<Identifier> textures = new ArrayList<>();

    // --- spin state ---
    private enum State { IDLE, SPINNING, STOPPING, STOPPED }
    private State state = State.IDLE;

    private double scrollPos = 0; // in pixels, grows forever while spinning
    private double velocity = 0;

    private long stateStartTime;
    private double stopStartPos;
    private double stopTargetPos;
    private long stopDurationMs;

    private int resultIndex = -1; // the index we must land on

    public boolean selected = false;

    public RollingEffectWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
        textures.add(Hud.getMobEffectSprite(MobEffects.NAUSEA));
        textures.add(Hud.getMobEffectSprite(MobEffects.POISON));
        textures.add(Hud.getMobEffectSprite(MobEffects.JUMP_BOOST));
        textures.add(Hud.getMobEffectSprite(MobEffects.ABSORPTION));
        textures.add(Hud.getMobEffectSprite(MobEffects.BAD_OMEN));
        textures.add(Hud.getMobEffectSprite(MobEffects.HEALTH_BOOST));
        textures.add(Hud.getMobEffectSprite(MobEffects.WATER_BREATHING));
        textures.add(Hud.getMobEffectSprite(MobEffects.GLOWING));
        textures.add(Hud.getMobEffectSprite(MobEffects.HERO_OF_THE_VILLAGE));
        textures.add(Hud.getMobEffectSprite(MobEffects.INFESTED));
        textures.add(Hud.getMobEffectSprite(MobEffects.STRENGTH));
        textures.add(Hud.getMobEffectSprite(MobEffects.LUCK));
    }

    /** Call this to kick off a roll that will land on `resultIndex`. */
    public void spin(int resultIndex) {
        this.resultIndex = resultIndex;
        this.state = State.SPINNING;
        this.velocity = 40; // px per "tick" of your choosing — tune to taste
        this.stateStartTime = System.currentTimeMillis();
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        long now = System.currentTimeMillis();
        updateAnimation(now);

        graphics.enableScissor(getX(), getY(), getX() + width, getY() + height);
        graphics.fill(getX(), getY(), getX() + width, getY() + height, 0xBB000000);

        if (isMouseOver(mouseX, mouseY) || selected) {
            graphics.outline(getX(), getY(), width, height, 0xFFFFFFFF);
        }

        int centerY = getY() + height / 2 - ITEM_SIZE / 2;

        // figure out which rows are visible and draw them by modulo index
        int firstVisibleRow = (int) Math.floor((scrollPos - height) / ITEM_HEIGHT) - 1;
        int lastVisibleRow  = (int) Math.floor((scrollPos + height) / ITEM_HEIGHT) + 1;

        for (int row = firstVisibleRow; row <= lastVisibleRow; row++) {
            int idx = Math.floorMod(row, textures.size());
            Identifier identifier = textures.get(idx);

            int y = centerY + (int) (row * ITEM_HEIGHT - scrollPos);
            int x = getX() + width / 2 - ITEM_SIZE / 2;

            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, identifier, x, y, ITEM_SIZE, ITEM_SIZE, ARGB.white(1.0f));
            graphics.outline(x, y, ITEM_SIZE, ITEM_SIZE, ARGB.color(0, 100, 125));
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {

    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (doubleClick) {
            spin(random.nextInt(textures.size()));
        }

        return super.mouseClicked(event, doubleClick);
    }

    private void updateAnimation(long now) {
        switch (state) {
            case SPINNING -> {
                scrollPos += velocity * 0.016; // assume ~60fps step; better: track real delta
                long elapsed = now - stateStartTime;
                //if (elapsed > 1200) { // spin for 1.2s before starting to stop
                    beginStopping(now);
               // }
            }
            case STOPPING -> {
                long elapsed = now - stateStartTime;
                double t = Math.min(1.0, elapsed / (double) stopDurationMs);
                double eased = easeOutCubic(t);
                scrollPos = lerp(stopStartPos, stopTargetPos, eased);
                if (t >= 1.0) {
                    scrollPos = stopTargetPos;
                    state = State.STOPPED;
                }
            }
            default -> {}
        }
    }

    private void beginStopping(long now) {
        state = State.STOPPING;
        stateStartTime = now;
        stopStartPos = scrollPos;
        stopDurationMs = 2000; // 2s to ease to a stop, tune to taste

        // find the *next* scrollPos value (ahead of current) where resultIndex
        // lands centered, plus a few extra full loops so it visibly "spins down"
        int listSize = textures.size();
        double currentRow = scrollPos / ITEM_HEIGHT;
        int currentRowFloor = (int) Math.floor(currentRow);

        // which row number (could be any multiple-of-listSize offset) gives resultIndex?
        int baseRowForResult = resultIndex; // row 0..listSize-1 maps directly
        // find smallest row >= currentRowFloor with (row mod listSize == resultIndex)
        int delta = Math.floorMod(baseRowForResult - currentRowFloor, listSize);
        int landingRow = currentRowFloor + delta;

        int extraLoops = 3; // spin past it a few extra times before settling
        landingRow += extraLoops * listSize;

        stopTargetPos = landingRow * ITEM_HEIGHT;
    }

    private static double easeOutCubic(double t) {
        double f = t - 1;
        return f * f * f + 1;
    }

    private static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }
}
