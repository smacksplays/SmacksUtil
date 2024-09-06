package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AbstractBackpackItem;

public class C2SBackpackOpenPacketHandler {

    public static void handle(C2SBackpackOpenPacket data, ServerPlayNetworking.Context context) {
        Player player = context.player();
        ItemStack stack = null;

        if (data.slot() == -1){
            //stack = CuriosApi.getCuriosHelper().findCurios(player, "back").get(0).stack();
        } else {
            stack = player.containerMenu.slots.get(data.slot()).getItem();
        }
        stack = player.containerMenu.slots.get(data.slot()).getItem();

        if (stack != null && stack.getItem() instanceof AbstractBackpackItem item) {
            player.openMenu(item.createScreenHandlerFactory(stack));
        }
    }
}
