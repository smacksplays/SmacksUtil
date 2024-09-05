package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AdvancedMagnetItem;
import net.smackplays.smacksutil.items.MagnetItem;

public class C2SToggleMagnetItemPacketHandler {
    public static void handle(C2SToggleMagnetItemPacket data, ServerPlayNetworking.Context context) {
        Player player = context.player();
        ItemStack stack = null;
        if (data.slot() == -1){
            // TODO TrinketsAPI
            //stack = CuriosApi.getCuriosHelper().findCurios(player, "charm").getFirst().stack();
        } else {
            stack = player.containerMenu.slots.get(data.slot()).getItem();
        }

        if (stack.getItem() instanceof MagnetItem item) {
            item.toggle(stack, player);
        } else if (stack.getItem() instanceof AdvancedMagnetItem item){
            item.toggle(stack, player);
        }
    }
}
