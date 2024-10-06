package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonBackpackSortPacketHandler;

public class C2SBackpackSortPacket {
    private final int slot;

    public C2SBackpackSortPacket(int slot) {
       this.slot = slot;
    }

    public C2SBackpackSortPacket(FriendlyByteBuf buffer) {
        slot = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(slot);
    }

    public void handle(CustomPayloadEvent.Context context) {
        if (context.getSender() != null) {
            C2SCommonBackpackSortPacketHandler.handle(context.getSender(), slot);
        }
    }
}