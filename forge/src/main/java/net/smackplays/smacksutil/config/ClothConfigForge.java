package net.smackplays.smacksutil.config;

import net.minecraft.network.chat.Component;
import net.minecraftforge.client.ConfigScreenHandler;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.platform.ForgeModConfig;

public class ClothConfigForge {
    public static void registerModsPage() {
        SmacksUtil.config.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (client, parent) ->
                                ForgeModConfig.create().setTitle(
                                        Component.literal("SmacksUtil")
                                ).setParentScreen(parent).build()));
    }
}