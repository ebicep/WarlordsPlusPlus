package com.ebicep.warlordsplusplus.forge.events;

import com.mojang.brigadier.Command;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientCommandRegistration {

    @SubscribeEvent
    public static void registerCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("wpp")
                        .executes(context -> {
                            ConfigBuilder builder = ConfigBuilder.create()
                                                                 .setParentScreen(null)
                                                                 .setTitle(Component.literal("warlordsplusplus.title"))
                                                                 .transparentBackground();
                            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
                            ConfigCategory general = builder.getOrCreateCategory(Component.translatable("general"));
                            general.addEntry(entryBuilder.startStrField(Component.translatable("test"), "test")
                                                         .setDefaultValue("This is the default value") // Recommended: Used when user click "Reset"
                                                         .setTooltip(Component.translatable("This option is awesome!")) // Optional: Shown when the user hover over this option
                                                         .build()); // Builds the option entry for cloth config

                            Screen screen = builder.build();
                            Minecraft.getInstance().setScreen(screen);
                            return Command.SINGLE_SUCCESS;
                        })
        );

    }
}
