package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonVeinMinerBreakPacketHandler;

public class C2SVeinMinerBreakPacket {
    private final BlockPos pos;
    private final boolean isCreative;
    private final boolean replaceSeeds;

    public C2SVeinMinerBreakPacket(BlockPos p, boolean isC, boolean isR) {
        pos = p;
        isCreative = isC;
        replaceSeeds = isR;
    }

    public C2SVeinMinerBreakPacket(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
        isCreative = buffer.readBoolean();
        replaceSeeds = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(isCreative);
        buffer.writeBoolean(replaceSeeds);
    }

    public void handle(CustomPayloadEvent.Context context) {
        if (context.getSender() != null) {
            C2SCommonVeinMinerBreakPacketHandler.handle(context.getSender(), context.getSender().level(), pos, isCreative, replaceSeeds);
        }
    }
}
