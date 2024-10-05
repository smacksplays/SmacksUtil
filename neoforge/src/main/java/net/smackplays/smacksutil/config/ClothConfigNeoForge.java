package net.smackplays.smacksutil.config;

import me.shedaniel.clothconfig2.ClothConfigDemo;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.smackplays.smacksutil.platform.NeoForgeModConfig;

public class ClothConfigNeoForge {
    public static void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (container, parent) ->
            NeoForgeModConfig.create().setTitle(Component.literal("SmacksUtil")).setParentScreen(parent).build());
    }
}