package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import top.theillusivec4.curios.api.CuriosApi;

public class C2SBackpackOpenPacketHandler {

    public static void handle(final C2SBackpackOpenPacket data, final IPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.enqueueWork(()  -> {
            Player player = context.player();
            ItemStack stack;
            if (data.slot() == -1){
                stack = CuriosApi.getCuriosHelper().findCurios(player, "back").get(0).stack();
            } else {
                stack = player.containerMenu.slots.get(data.slot()).getItem();
            }

            if (stack != null && stack.getItem() instanceof AbstractBackpackItem item) {
                player.openMenu(item.createScreenHandlerFactory(stack));
            }
        });
    }
}
