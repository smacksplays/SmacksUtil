package net.smackplays.smacksutil.networking.c2shandlers;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AutoLightWandItem;
import net.smackplays.smacksutil.platform.Services;

public class C2SCommonToggleLightWandItemPacketHandler {
    public static void handle(ServerPlayer player, int slot) {
        ItemStack stack;
        if (slot == -1){
            stack = Services.PLATFORM.getTrinketOrCuriosStack(player, "hands");
        } else {
            stack = player.containerMenu.slots.get(slot).getItem();
        }

        if (stack != null && stack.getItem() instanceof AutoLightWandItem item) {
            item.toggle(stack, player);
        }
    }
}
