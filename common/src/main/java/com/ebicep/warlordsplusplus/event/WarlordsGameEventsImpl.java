package com.ebicep.warlordsplusplus.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.time.Instant;

@Environment(EnvType.CLIENT)
public interface WarlordsGameEventsImpl {

    Event<ResetEvent> RESET_EVENT = new Event<>(ResetEvent.class,
            (listeners) -> (event) -> {
                for (ResetEvent listener : listeners) {
                    listener.onReset(event);
                }
            }
    );

    Event<GameEndEvent> GAME_END_EVENT = new Event<>(GameEndEvent.class,
            (listeners) -> (event) -> {
                for (GameEndEvent listener : listeners) {
                    listener.onGameEnd(event);
                }
            }
    );

    Event<MinuteEvent> MINUTE_EVENT = new Event<>(MinuteEvent.class,
            (listeners) -> (event) -> {
                for (MinuteEvent listener : listeners) {
                    listener.onMinute(event);
                }
            }
    );

    Event<SecondEvent> SECOND_EVENT = new Event<>(SecondEvent.class,
            (listeners) -> (event) -> {
                for (SecondEvent listener : listeners) {
                    listener.onSecond(event);
                }
            }
    );

    @Environment(EnvType.CLIENT)
    interface ResetEvent {
        void onReset(WarlordsGameEvents.ResetEvent resetEvent);
    }
    @Environment(EnvType.CLIENT)
    interface GameEndEvent {
        void onGameEnd(WarlordsGameEvents.GameEndEvent gameEndEvent);
    }
    @Environment(EnvType.CLIENT)
    interface MinuteEvent {
        void onMinute(WarlordsGameEvents.MinuteEvent minuteEvent);
    }
    @Environment(EnvType.CLIENT)
    interface SecondEvent {
        void onSecond(WarlordsGameEvents.SecondEvent secondEvent);
    }

}
