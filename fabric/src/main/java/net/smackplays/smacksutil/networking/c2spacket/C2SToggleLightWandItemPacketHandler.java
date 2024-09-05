package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AutoLightWandItem;

public class C2SToggleLightWandItemPacketHandler {
    public static void handle(C2SToggleLightWandItemPacket data, ServerPlayNetworking.Context context) {
        Player player = context.player();
        ItemStack stack = null;
        if (data.slot() == -1){
            // TODO Trinekts
            //stack = CuriosApi.getCuriosHelper().findCurios(player, "hands").get(0).stack();
        } else {
            stack = player.containerMenu.slots.get(data.slot()).getItem();
        }

        if (stack != null && stack.getItem() instanceof AutoLightWandItem item) {
            item.toggle(stack, player);
        }
    }
}
