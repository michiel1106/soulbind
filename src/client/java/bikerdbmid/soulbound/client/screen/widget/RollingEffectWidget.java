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
    List<Identifier> textures = new ArrayList<>();


    public boolean selected = false;
    int offset = 0;

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
        textures.add(Hud.getMobEffectSprite(MobEffects.UNLUCK));
        textures.add(Hud.getMobEffectSprite(MobEffects.LUCK));


    }



    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.enableScissor(getX(), getY(), getX() + width, getY() + height);
        graphics.fill(getX(), getY(), getX() + width, getY() + height, 0xBB000000);

        if (isMouseOver(mouseX, mouseY) || selected) {
            graphics.outline(getX(), getY(), width, height, 0xFFFFFFFF);
        }

        int SIZE = 100;
        offset -= 3;
        int offsetY = offset % 2000;


        for (int i = 0; i < textures.size(); i++) {
            Identifier identifier = textures.get(i);

            int OFFSETTHING = i*110;

            int x = getX() + width/2 - SIZE/2;
            int y = (getY() - SIZE/2) + offsetY;
            y += OFFSETTHING;

            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, identifier, x, y, SIZE, SIZE, ARGB.white(1.0f));
            graphics.outline(x, y, SIZE, SIZE, ARGB.color(0, 100, 125));

        }


        //graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Hud.getMobEffectSprite(MobEffects.NAUSEA), getX() + width/2 - SIZE/2, getY() + height/2 - SIZE/2 + offsetY, SIZE, SIZE, ARGB.white(1.0f));


    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {

    }

}
