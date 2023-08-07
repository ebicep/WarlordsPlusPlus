package com.ebicep.warlordsplusplus.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface WarlordsPlayerEventsImpl {

    Event<AbstractDamageHealEnergyEvent> ABSTRACT_DAMAGE_HEAL_ENERGY_EVENT = new Event<>(AbstractDamageHealEnergyEvent.class,
            (listeners) -> (event) -> {
                for (AbstractDamageHealEnergyEvent listener : listeners) {
                    listener.onDamageHealEnergy(event);
                }
            }
    );
    Event<HealingGivenEvent> HEALING_GIVEN_EVENT = new Event<>(HealingGivenEvent.class,
            (listeners) -> (event) -> {
                for (HealingGivenEvent listener : listeners) {
                    listener.onHealingGiven(event);
                }
            }
    );

    Event<HealingReceivedEvent> HEALING_RECEIVED_EVENT = new Event<>(HealingReceivedEvent.class,
            (listeners) -> (event) -> {
                for (HealingReceivedEvent listener : listeners) {
                    listener.onHealingReceived(event);
                }
            }
    );

    Event<DamageDoneEvent> DAMAGE_DONE_EVENT = new Event<>(DamageDoneEvent.class,
            (listeners) -> (event) -> {
                for (DamageDoneEvent listener : listeners) {
                    listener.onDamageDone(event);
                }
            }
    );

    Event<DamageTakenEvent> DAMAGE_TAKEN_EVENT = new Event<>(DamageTakenEvent.class,
            (listeners) -> (event) -> {
                for (DamageTakenEvent listener : listeners) {
                    listener.onDamageTaken(event);
                }
            }
    );

    Event<DamageAbsorbedEvent> DAMAGE_ABSORBED_EVENT = new Event<>(DamageAbsorbedEvent.class,
            (listeners) -> (event) -> {
                for (DamageAbsorbedEvent listener : listeners) {
                    listener.onDamageAbsorbed(event);
                }
            }
    );

    Event<EnergyGivenEvent> ENERGY_GIVEN_EVENT = new Event<>(EnergyGivenEvent.class,
            (listeners) -> (event) -> {
                for (EnergyGivenEvent listener : listeners) {
                    listener.onEnergyGiven(event);
                }
            }
    );

    Event<EnergyLostEvent> ENERGY_LOST_EVENT = new Event<>(EnergyLostEvent.class,
            (listeners) -> (event) -> {
                for (EnergyLostEvent listener : listeners) {
                    listener.onEnergyLost(event);
                }
            }
    );

    Event<EnergyReceivedEvent> ENERGY_RECEIVED_EVENT = new Event<>(EnergyReceivedEvent.class,
            (listeners) -> (event) -> {
                for (EnergyReceivedEvent listener : listeners) {
                    listener.onEnergyReceived(event);
                }
            }
    );

    Event<EnergyStolenEvent> ENERGY_STOLEN_EVENT = new Event<>(EnergyStolenEvent.class,
            (listeners) -> (event) -> {
                for (EnergyStolenEvent listener : listeners) {
                    listener.onEnergyStolen(event);
                }
            }
    );

    Event<KillEvent> KILL_EVENT = new Event<>(KillEvent.class,
            (listeners) -> (event) -> {
                for (KillEvent listener : listeners) {
                    listener.onKill(event);
                }
            }
    );

    Event<KillStealEvent> KILL_STEAL_EVENT = new Event<>(KillStealEvent.class,
            (listeners) -> (event) -> {
                for (KillStealEvent listener : listeners) {
                    listener.onKillSteal(event);
                }
            }
    );

    Event<HitEvent> HIT_EVENT = new Event<>(HitEvent.class,
            (listeners) -> (event) -> {
                for (HitEvent listener : listeners) {
                    listener.onHit(event);
                }
            }
    );



    @Environment(EnvType.CLIENT)
    interface AbstractDamageHealEnergyEvent {
        void onDamageHealEnergy(WarlordsPlayerEvents.AbstractDamageHealEnergyEvent abstractDamageHealEnergyEvent);
    }

    @Environment(EnvType.CLIENT)
    interface HealingGivenEvent {
        void onHealingGiven(WarlordsPlayerEvents.HealingGivenEvent healingGivenEvent);
    }

    @Environment(EnvType.CLIENT)
    interface HealingReceivedEvent {
        void onHealingReceived(WarlordsPlayerEvents.HealingReceivedEvent healingReceivedEvent);
    }

    @Environment(EnvType.CLIENT)
    interface DamageDoneEvent {
        void onDamageDone(WarlordsPlayerEvents.DamageDoneEvent damageDoneEvent);
    }

    @Environment(EnvType.CLIENT)
    interface DamageTakenEvent {
        void onDamageTaken(WarlordsPlayerEvents.DamageTakenEvent damageTakenEvent);
    }

    @Environment(EnvType.CLIENT)
    interface DamageAbsorbedEvent {
        void onDamageAbsorbed(WarlordsPlayerEvents.DamageAbsorbedEvent damageAbsorbedEvent);
    }

    @Environment(EnvType.CLIENT)
    interface EnergyGivenEvent {
        void onEnergyGiven(WarlordsPlayerEvents.EnergyGivenEvent energyGivenEvent);
    }

    @Environment(EnvType.CLIENT)
    interface EnergyLostEvent {
        void onEnergyLost(WarlordsPlayerEvents.EnergyLostEvent energyLostEvent);
    }

    @Environment(EnvType.CLIENT)
    interface EnergyReceivedEvent {
        void onEnergyReceived(WarlordsPlayerEvents.EnergyReceivedEvent energyReceivedEvent);
    }

    @Environment(EnvType.CLIENT)
    interface EnergyStolenEvent {
        void onEnergyStolen(WarlordsPlayerEvents.EnergyStolenEvent energyStolenEvent);
    }

    @Environment(EnvType.CLIENT)
    interface KillEvent {
        void onKill(WarlordsPlayerEvents.KillEvent killEvent);
    }

    @Environment(EnvType.CLIENT)
    interface KillStealEvent {
        void onKillSteal(WarlordsPlayerEvents.KillStealEvent killStealEvent);
    }

    @Environment(EnvType.CLIENT)
    interface HitEvent {
        void onHit(WarlordsPlayerEvents.HitEvent hitEvent);
    }

}

