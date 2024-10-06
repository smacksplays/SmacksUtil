package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonInteractEntityPacketHandler;

public class C2SInteractEntityPacket {
    private final String entityUUID;
    private final boolean isMainHnad;

    public C2SInteractEntityPacket(String u, boolean m) {
        entityUUID = u;
        isMainHnad = m;
    }

    public C2SInteractEntityPacket(FriendlyByteBuf buffer) {
        entityUUID = buffer.readUtf();
        isMainHnad = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(entityUUID);
        buffer.writeBoolean(isMainHnad);
    }

    public void handle(CustomPayloadEvent.Context context) {
        if (context.getSender() != null) {
            C2SCommonInteractEntityPacketHandler.handle(context.getSender(), entityUUID, isMainHnad);
        }
    }
}
