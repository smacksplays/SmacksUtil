package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import net.smackplays.smacksutil.items.LargeBackpackItem;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import org.jetbrains.annotations.NotNull;

public class C2SBackpackSortPacketHandler {

    public static void handle(@NotNull C2SBackpackSortPacket customPacketPayload, IPayloadContext context) {
        // Do something with the data, on the main thread
        context.enqueueWork(()  -> {
            Player player = context.player();
            AbstractContainerMenu screenHandler = player.containerMenu;
            ItemStack stack = player.getInventory().getItem(customPacketPayload.slot());
            if (stack.getItem() instanceof LargeBackpackItem && screenHandler instanceof LargeBackpackMenu lBackpackMenu) {
                lBackpackMenu.sort();
            } else if (stack.getItem() instanceof AbstractBackpackItem && screenHandler instanceof BackpackMenu backpackMenu) {
                backpackMenu.sort();
            }
        });
    }
}
