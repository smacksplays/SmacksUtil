package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonBackpackOpenPacketHandler;

public class C2SBackpackOpenPacket {
    private final int slot;

    public C2SBackpackOpenPacket(int s) {
        slot = s;
    }

    public C2SBackpackOpenPacket(FriendlyByteBuf buffer) {
        slot = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(slot);
    }

    public void handle(CustomPayloadEvent.Context context) {
        if (context.getSender() != null) {
            C2SCommonBackpackOpenPacketHandler.handle(context.getSender(), slot);
        }
    }
}