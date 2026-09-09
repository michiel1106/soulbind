package bikerdbmid.soulbound.client.screen;

import bikerdbmid.soulbound.client.screen.widget.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.input.*;
import net.minecraft.network.chat.*;

import java.util.*;

public class EffectSelectionScreen extends Screen {
    Random random = new Random();
    RollingEffectWidget rollingEffectWidget1;
    RollingEffectWidget rollingEffectWidget2;
    RollingEffectWidget rollingEffectWidget3;
    Button button;

    public EffectSelectionScreen() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        super.init();

        rollingEffectWidget1 = new RollingEffectWidget((int) (width*0.055), (int) (height*0.12), (int) (width*0.287), (int) (height*0.75));
        rollingEffectWidget2 = new RollingEffectWidget((int) (width*0.355), (int) (height*0.12), (int) (width*0.287), (int) (height*0.75));
        rollingEffectWidget3 = new RollingEffectWidget((int) (width*0.655), (int) (height*0.12), (int) (width*0.287), (int) (height*0.75));
        button = Button.builder(Component.literal("Roll Effects"), (button1 -> {
            Set<Integer> list = new HashSet<>();
            while (list.size() < 4) {
                int i = random.nextInt(rollingEffectWidget1.getListSize());
                list.add(i);
            }
            rollingEffectWidget1.spin((Integer) list.toArray()[0]);
            rollingEffectWidget2.spin((Integer) list.toArray()[1]);
            rollingEffectWidget3.spin((Integer) list.toArray()[2]);


        })).build();

        button.setPosition((int) (width * 0.5 - button.getWidth() / 2), (int) (height * 0.95 - button.getHeight()));


        addRenderableWidget(rollingEffectWidget1);
        addRenderableWidget(rollingEffectWidget2);
        addRenderableWidget(rollingEffectWidget3);
        //addRenderableWidget(button);

    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.enableScissor(0, (int) (height*0.12), (int) (width), (int) (height*0.869));
        super.extractRenderState(graphics, mouseX, mouseY, a);
        graphics.disableScissor();
        button.extractRenderState(graphics, mouseX, mouseY, a);
    }


    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        Optional<GuiEventListener> childAt = getChildAt(event.x(), event.y());
        if (childAt.isPresent()) {
            if (childAt.get() instanceof RollingEffectWidget widget) {
                rollingEffectWidget1.selected = false;
                rollingEffectWidget2.selected = false;
                rollingEffectWidget3.selected = false;
                widget.selected = true;
            } else {
                rollingEffectWidget1.selected = false;
                rollingEffectWidget2.selected = false;
                rollingEffectWidget3.selected = false;
            }
        } else {
            rollingEffectWidget1.selected = false;
            rollingEffectWidget2.selected = false;
            rollingEffectWidget3.selected = false;
        }

        if (button.isMouseOver(event.x(), event.y())) {
            button.mouseClicked(event, doubleClick);
        }

        return super.mouseClicked(event, doubleClick);
    }


}
