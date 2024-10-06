package net.smackplays.smacksutil.networking.c2shandlers;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import net.smackplays.smacksutil.platform.Services;

public class C2SCommonBackpackOpenPacketHandler {
    public static void handle(ServerPlayer player, int slot) {
        ItemStack stack;
        if (slot == -1){
            stack = Services.PLATFORM.getTrinketOrCuriosStack(player, "back");
        } else {
            stack = player.containerMenu.slots.get(slot).getItem();
        }

        if (stack != null && stack.getItem() instanceof AbstractBackpackItem item) {
            player.openMenu(item.createScreenHandlerFactory(stack));
        }
    }
}
