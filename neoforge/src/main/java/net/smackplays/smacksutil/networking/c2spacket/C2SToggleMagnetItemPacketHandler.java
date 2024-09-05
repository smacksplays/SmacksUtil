package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.items.AdvancedMagnetItem;
import net.smackplays.smacksutil.items.MagnetItem;
import top.theillusivec4.curios.api.CuriosApi;

public class C2SToggleMagnetItemPacketHandler {
    public static void handle(final C2SToggleMagnetItemPacket data, final IPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.enqueueWork(()  -> {
            Player player = context.player();
            ItemStack stack;
            if (data.slot() == -1){
                stack = CuriosApi.getCuriosHelper().findCurios(player, "charm").getFirst().stack();
            } else {
                stack = player.containerMenu.slots.get(data.slot()).getItem();
            }

            if (stack.getItem() instanceof MagnetItem item) {
                item.toggle(stack, player);
            } else if (stack.getItem() instanceof AdvancedMagnetItem item){
                item.toggle(stack, player);
            }
        });
    }
}
