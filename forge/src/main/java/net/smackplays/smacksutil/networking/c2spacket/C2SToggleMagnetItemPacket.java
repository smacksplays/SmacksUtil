package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.items.AdvancedMagnetItem;
import net.smackplays.smacksutil.items.MagnetItem;

public class C2SToggleMagnetItemPacket {
    private final int slot;

    public C2SToggleMagnetItemPacket(int s) {
        slot = s;
    }

    public C2SToggleMagnetItemPacket(FriendlyByteBuf buffer) {
        slot = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(slot);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null)
            return;
        ItemStack stack = null;
        if (slot == -1){
            // TODO Curios currently not available
            //stack = CuriosApi.getCuriosHelper().findCurios(player, "charm").getFirst().stack();
        } else {
            stack = player.containerMenu.slots.get(slot).getItem();
        }
        if (stack != null) {
            if (stack.getItem() instanceof MagnetItem item) {
                item.toggle(stack, player);
            } else if (stack.getItem() instanceof AdvancedMagnetItem item){
                item.toggle(stack, player);
            }
        }
    }
}