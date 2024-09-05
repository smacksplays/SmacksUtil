package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import net.smackplays.smacksutil.items.LargeBackpackItem;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;

public class C2SBackpackSortPacketHandler {

    public static void handle(C2SBackpackSortPacket customPacketPayload, ServerPlayNetworking.Context context) {
        Player player = context.player();
        AbstractContainerMenu screenHandler = player.containerMenu;
        ItemStack stack = player.getInventory().getItem(customPacketPayload.slot());
        if (stack.getItem() instanceof LargeBackpackItem && screenHandler instanceof LargeBackpackMenu lBackpackMenu) {
            lBackpackMenu.sort();
        } else if (stack.getItem() instanceof AbstractBackpackItem && screenHandler instanceof BackpackMenu backpackMenu) {
            backpackMenu.sort();
        }
    }
}
