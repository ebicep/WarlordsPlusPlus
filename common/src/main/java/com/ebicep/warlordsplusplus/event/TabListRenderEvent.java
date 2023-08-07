package com.ebicep.warlordsplusplus.event;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import org.jetbrains.annotations.Nullable;

public interface TabListRenderEvent {

    //playertaboverla=
    Event<TabListRenderEvent> TAB_LIST_RENDER_PRE = new Event<>(TabListRenderEvent.class,
            (listeners) -> (guiGraphics, i, scoreboard, objective) -> {
                for (TabListRenderEvent listener : listeners) {
                    InteractionResult result = listener.onTabListRender(guiGraphics, i, scoreboard, objective);
                    if (result != InteractionResult.PASS) {
                        return result;
                    }
                }
                return InteractionResult.PASS;
            }
    );

    InteractionResult onTabListRender(GuiGraphics guiGraphics, int i, Scoreboard scoreboard, @Nullable Objective objective);

}