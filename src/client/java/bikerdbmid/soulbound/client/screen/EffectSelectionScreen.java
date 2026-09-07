package bikerdbmid.soulbound.client.screen;

import bikerdbmid.soulbound.client.screen.widget.*;
import net.minecraft.client.gui.components.events.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.input.*;
import net.minecraft.network.chat.*;

import java.util.*;

public class EffectSelectionScreen extends Screen {
    RollingEffectWidget rollingEffectWidget1;
    RollingEffectWidget rollingEffectWidget2;
    RollingEffectWidget rollingEffectWidget3;

    public EffectSelectionScreen() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        super.init();

        rollingEffectWidget1 = new RollingEffectWidget((int) (width*0.055), (int) (height*0.12), (int) (width*0.287), (int) (height*0.75));
        rollingEffectWidget2 = new RollingEffectWidget((int) (width*0.355), (int) (height*0.12), (int) (width*0.287), (int) (height*0.75));
        rollingEffectWidget3 = new RollingEffectWidget((int) (width*0.655), (int) (height*0.12), (int) (width*0.287), (int) (height*0.75));

        addRenderableWidget(rollingEffectWidget1);
      //  addRenderableWidget(rollingEffectWidget2);
       // addRenderableWidget(rollingEffectWidget3);

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

        return super.mouseClicked(event, doubleClick);
    }


}
