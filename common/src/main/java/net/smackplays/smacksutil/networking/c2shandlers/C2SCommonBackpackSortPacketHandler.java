package net.smackplays.smacksutil.networking.c2shandlers;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import net.smackplays.smacksutil.menus.AbstractBackpackMenu;
import net.smackplays.smacksutil.menus.AbstractLargeBackpackMenu;
import net.smackplays.smacksutil.platform.Services;

public class C2SCommonBackpackSortPacketHandler {
    public static void handle(ServerPlayer player, int slot) {
        AbstractContainerMenu screenHandler = player.containerMenu;
        ItemStack stack;
        if (slot == -1){
            stack = Services.PLATFORM.getTrinketorCuriosStack(player, "back");
        } else {
            stack = player.getInventory().getItem(slot);
        }
        if (AbstractBackpackItem.class.isAssignableFrom(stack.getItem().getClass())) {
            if (AbstractBackpackMenu.class.isAssignableFrom(screenHandler.getClass())) {
                AbstractBackpackMenu BackpackMenu = (AbstractBackpackMenu)screenHandler;
                BackpackMenu.sort();
            } else if (AbstractLargeBackpackMenu.class.isAssignableFrom(screenHandler.getClass())) {
                AbstractLargeBackpackMenu BackpackMenu = (AbstractLargeBackpackMenu)screenHandler;
                BackpackMenu.sort();
            }
        }
    }
}
