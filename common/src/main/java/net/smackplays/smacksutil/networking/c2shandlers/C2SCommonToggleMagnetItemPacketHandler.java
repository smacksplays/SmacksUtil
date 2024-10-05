package net.smackplays.smacksutil.networking.c2shandlers;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AdvancedMagnetItem;
import net.smackplays.smacksutil.items.MagnetItem;
import net.smackplays.smacksutil.platform.Services;

public class C2SCommonToggleMagnetItemPacketHandler {
    public static void handle(ServerPlayer player, int slot) {
        ItemStack stack = null;
        if (slot == -1){
            // TODO fix use Curios or Trinkets API
            //stack = CuriosApi.getCuriosHelper().findCurios(player, "charm").getFirst().stack();
            stack = Services.PLATFORM.getTrinketorCuriosStack(player, "charm");
        } else {
            stack = player.containerMenu.slots.get(slot).getItem();
        }

        if (stack.getItem() instanceof MagnetItem item) {
            item.toggle(stack, player);
        } else if (stack.getItem() instanceof AdvancedMagnetItem item){
            item.toggle(stack, player);
        }
    }
}
