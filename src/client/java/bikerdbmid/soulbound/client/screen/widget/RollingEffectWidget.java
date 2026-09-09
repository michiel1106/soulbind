package bikerdbmid.soulbound.client.screen.widget;

import bikerdbmid.soulbound.components.content.buffs.custom.*;
import bikerdbmid.soulbound.components.content.debuffs.custom.*;
import bikerdbmid.soulbound.components.content.effect.*;
import bikerdbmid.soulbound.components.content.effect.custom.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.narration.*;
import net.minecraft.client.input.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class RollingEffectWidget extends AbstractWidget {
    private static Random random = new Random();


    private final int itemSize;
    private final int itemGap;
    private final int itemHeight;

    private static final int ITEM_SIZE = 100;
    private static final int ITEM_GAP = 10;
    private static final int ITEM_HEIGHT = ITEM_SIZE + ITEM_GAP; // 110

    List<Effect> effects = new ArrayList<>();

    // --- spin state ---
    private enum State { IDLE, SPINNING, STOPPING, STOPPED }
    private State state = State.IDLE;

    private double scrollPos = 0; // in pixels, grows forever while spinning
    private double velocity = 0;

    private long stateStartTime;
    private double stopStartPos;
    private double stopTargetPos;
    private long stopDurationMs;

    private int tempInt = 0;

    private int resultIndex = -1; // the index we must land on

    public boolean selected = false;

    public RollingEffectWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
        effects.addAll(ModEffects.getAllEffectsExceptEmpty());

        this.itemSize = Math.min(width, height / 3); // pick whatever ratio fits your layout
        this.itemGap = Math.max(4, itemSize / 10);
        this.itemHeight = itemSize + itemGap;

       // Collections.shuffle(effects);

    }

    public int getListSize() {
        return effects.size();
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


        graphics.fill(getX(), getY(), getX() + width, getY() + height, 0xBB000000);



        int centerY = getY() + height / 2 - ITEM_SIZE / 2;

        // figure out which rows are visible and draw them by modulo index
        int firstVisibleRow = (int) Math.floor((scrollPos - height) / ITEM_HEIGHT) - 1;
        int lastVisibleRow  = (int) Math.floor((scrollPos + height) / ITEM_HEIGHT) + 1;

        for (int row = firstVisibleRow; row <= lastVisibleRow; row++) {
            int idx = Math.floorMod(row, effects.size());
            Effect effect = getEffect(idx);
            Identifier identifier = effect.getImage();

            int y = centerY + (int) (row * ITEM_HEIGHT - scrollPos);
            int x = getX() + width / 2 - ITEM_SIZE / 2;

            switch (effect.getImgRenderType()) {
                case BLOCK -> renderBlock(graphics, x, y, ITEM_SIZE, effect.getBlock());
                case ITEM -> renderItem(graphics, x, y, ITEM_SIZE, effect.getItem());
                case IMAGE -> renderImage(graphics, x, y, effect.getImage());
            }

            if (mouseX >= x && mouseY >= y && mouseX < x + ITEM_SIZE && mouseY < y + ITEM_SIZE) {
                if (isMouseOver(mouseX, mouseY)) {
                    renderTooltip(graphics, mouseX, mouseY, effect, a);
                }
            }

            graphics.outline(x, y, ITEM_SIZE, ITEM_SIZE, ARGB.color(0, 100, 125));
        }

        if (isMouseOver(mouseX, mouseY) || selected) {
            graphics.outline(getX(), getY(), width, height, 0xFFFFFFFF);
        }
    }

    private void renderImage(GuiGraphicsExtractor graphics, int x, int y, @Nullable Identifier identifier) {
        if (identifier == null) return;

        if (!identifier.getPath().endsWith(".png")) {
            identifier = Identifier.fromNamespaceAndPath(identifier.getNamespace(), identifier.getPath() + ".png");
        }

        AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(identifier);
        int srcWidth = texture.getTexture().getWidth(0);
        int srcHeight = texture.getTexture().getHeight(0);

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                identifier,
                x, y,
                0f, 0f,
                ITEM_SIZE, ITEM_SIZE,
                srcWidth, srcHeight,
                srcWidth, srcHeight
        );


    }

    private void renderItem(GuiGraphicsExtractor graphics, int x, int y, int itemSize, @Nullable Item item) {
        if (item == null) return;

        float scale = itemSize / 16f;

        graphics.pose().pushMatrix();
        graphics.pose().translate(x, y);
        graphics.pose().scale(scale, scale);
        graphics.item(item.getDefaultInstance(), 0, 0);
        graphics.pose().popMatrix();
    }

    private void renderBlock(GuiGraphicsExtractor graphics, int x, int y, int itemSize, @Nullable Block block) {
        if (block == null) return;
        graphics.item(block.asItem().getDefaultInstance(), x, y);
    }

    private void renderTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY, Effect effect, float a) {

        List<Component> list = new ArrayList<>(List.of(
                Component.translatable("soulbound.powers." + effect.id + ".title"),
                Component.empty(),
                Component.translatable("soulbound.powers." + effect.id + ".description1"),
                Component.translatable("soulbound.powers." + effect.id + ".description2"),
                Component.translatable("soulbound.powers." + effect.id + ".description3"),
                Component.empty()
        ));

        list.add(Component.literal("Buffs:"));
        for (Buff buff : effect.getBuffs()) {
            list.add(Component.translatable("soulbound.buffs." + buff.id + ".title"));
        }

        list.add(Component.literal("Debuffs:"));
        for (DeBuff debuff : effect.getDebuffs()) {
            list.add(Component.translatable("soulbound.debuffs." + debuff.id + ".title"));
        }

        graphics.setTooltipForNextFrame(
                Minecraft.getInstance().font,
                list,
                Optional.empty(),
                mouseX,
                mouseY);
    }

    private Effect getEffect(int idx) {
        return effects.get(idx);
    }


    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {

    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (doubleClick) {
            spin(random.nextInt(effects.size()));
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
        int listSize = effects.size();
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
