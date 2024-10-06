package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonSetBlockAirPacketHandler;

public class C2SSetBlockAirPacket {
    private final BlockPos pos;

    public C2SSetBlockAirPacket(BlockPos p) {
        pos = p;
    }

    public C2SSetBlockAirPacket(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }

    public void handle(CustomPayloadEvent.Context context) {
        if (context.getSender() != null) {
            C2SCommonSetBlockAirPacketHandler.handle(context.getSender().level(), pos);
        }
    }
}
