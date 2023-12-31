package net.smackplays.smacksutil.platform.services;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.smackplays.smacksutil.platform.Services;

public abstract class IKeyHandler {
    public static final String KEY_CATEGORY_SMACKSUTIL = "key.category.smacksutil";
    public static final String KEY_SMACKSUTIL_VEINACTIVATE = "key.smacksutil.veinactivate";
    public static final String KEY_SMACKSUTIL_VEINPREVIEW = "key.smacksutil.veinpreview";
    public static final String KEY_SMACKSUTIL_FASTPLACE = "key.smacksutil.fastplace";
    public static final String KEY_SMACKSUTIL_EXACTMATCH = "key.smacksutil.exactmatch";
    public static final int GREEN = 65280;
    public static final int RED = 16711680;

    public static KeyMapping veinKey;
    public static KeyMapping veinPreviewKey;
    public static KeyMapping fastPlaceKey;
    public static KeyMapping exactMatchKey;

    public static void veinPreviewConsume() {
        if (veinPreviewKey.consumeClick()) {
            Services.VEIN_MINER.togglePreview();
            String str = Services.VEIN_MINER.renderPreview ? "Active" : "Inactive";
            int color = Services.VEIN_MINER.renderPreview ? GREEN : RED;
            Minecraft.getInstance().player.displayClientMessage(Component
                    .literal("Veinminer Preview: " + str).withColor(color), true);
        }
    }

    public static void fastPlaceConsume() {
        if (fastPlaceKey.consumeClick()) {
            Services.CONFIG.setEnabledFastPlace(!Services.CONFIG.isEnabledFastPlace());
            String str = Services.CONFIG.isEnabledFastPlace() ? "Active" : "Inactive";
            int color = Services.CONFIG.isEnabledFastPlace() ? GREEN : RED;
            Minecraft.getInstance().player.displayClientMessage(Component
                    .literal("Fastplace: " + str).withColor(color), true);
        }
    }

    public static void exactMatchConsume() {
        if (exactMatchKey.consumeClick()) {
            Services.VEIN_MINER.toggleExactMatch();
            String str = Services.VEIN_MINER.isExactMatch() ? "Active" : "Inactive";
            int color = Services.VEIN_MINER.isExactMatch() ? GREEN : RED;
            Minecraft.getInstance().player.displayClientMessage(Component
                    .literal("Exact Match: " + str).withColor(color), true);
        }
    }

    public void register() {
    }
}
