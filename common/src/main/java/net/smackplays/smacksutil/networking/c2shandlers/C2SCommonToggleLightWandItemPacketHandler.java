package net.smackplays.smacksutil.networking.c2shandlers;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AutoLightWandItem;

public class C2SCommonToggleLightWandItemPacketHandler {
    public static void handle(ServerPlayer player, int slot) {
        ItemStack stack = player.containerMenu.slots.get(slot).getItem();

        if (stack.getItem() instanceof AutoLightWandItem item) {
            item.toggle(stack, player);
        }
    }
}
