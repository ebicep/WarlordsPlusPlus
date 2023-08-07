package com.ebicep.warlordsplusplus.mixin;

import com.ebicep.warlordsplusplus.event.TabListRenderEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.world.InteractionResult;
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
        InteractionResult interactionResult = TabListRenderEvent.TAB_LIST_RENDER_PRE.invoker().onTabListRender(
                guiGraphics,
                i,
                scoreboard,
                objective
        );
        if (interactionResult == InteractionResult.FAIL) {
            callbackInfo.cancel();
        }
    }


}
