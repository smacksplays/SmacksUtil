package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import net.smackplays.smacksutil.menus.AbstractBackpackMenu;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonBackpackSortPacketHandler;

public class C2SBackpackSortPacketHandler {

    public static void handle(C2SBackpackSortPacket data, ServerPlayNetworking.Context context) {
        C2SCommonBackpackSortPacketHandler.handle(context.player(), data.slot());
    }
}
