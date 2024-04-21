package com.ebicep.warlordsplusplus.mixin;

import com.ebicep.chatplus.events.EventBus;
import com.ebicep.warlordsplusplus.events.RenderJumpMeterEvent;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.PlayerRideableJumping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {

    @Inject(
            method = "renderJumpMeter",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void renderJumpMeter(PlayerRideableJumping playerRideableJumping, GuiGraphics guiGraphics, int i, CallbackInfo ci) {
        if (EventBus.INSTANCE.post(RenderJumpMeterEvent.class, new RenderJumpMeterEvent(playerRideableJumping, guiGraphics, i, false)).getCancel()) {
            ci.cancel();
        }
    }

}
