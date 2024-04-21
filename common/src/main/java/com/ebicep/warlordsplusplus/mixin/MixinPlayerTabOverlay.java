package com.ebicep.warlordsplusplus.mixin;

import com.ebicep.chatplus.events.EventBus;
import com.ebicep.warlordsplusplus.events.TabListRenderEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTabOverlay.class)
public class MixinPlayerTabOverlay {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphics guiGraphics, int i, Scoreboard scoreboard, @Nullable Objective objective, CallbackInfo callbackInfo) {
        if (EventBus.INSTANCE.post(TabListRenderEvent.class, new TabListRenderEvent(guiGraphics, i, scoreboard, objective, false)).getCancel()) {
            callbackInfo.cancel();
        }
    }


}
