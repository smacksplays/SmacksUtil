package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import net.smackplays.smacksutil.items.LargeBackpackItem;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonBackpackOpenPacketHandler;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonBackpackSortPacketHandler;
import org.jetbrains.annotations.NotNull;

public class C2SBackpackSortPacketHandler {

    public static void handle(@NotNull C2SBackpackSortPacket data, IPayloadContext context) {
        C2SCommonBackpackSortPacketHandler.handle((ServerPlayer) context.player(), data.slot());
    }
}
