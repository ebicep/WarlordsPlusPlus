package com.ebicep.warlordsplusplus.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public interface PlayerRenderEvent {
	Event<PlayerRenderEvent> PLAYER_RENDER_POST = new Event<>(PlayerRenderEvent.class,
		(listeners) -> (poseStack, multiBufferSource, player) -> {
			for (PlayerRenderEvent listener : listeners) {
				InteractionResult result = listener.onPlayerRender(poseStack, multiBufferSource, player);
				if (result != InteractionResult.PASS) {
					return result;
				}
			}
			return InteractionResult.PASS;
		}
	);

	InteractionResult onPlayerRender(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, Player entity);

}