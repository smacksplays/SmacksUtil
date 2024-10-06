package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonSetBlockAirPacketHandler;

public class C2SSetBlockAirPacketHandler {
    public static void handle(C2SSetBlockAirPacket data, IPayloadContext context) {
        BlockPos pos = new BlockPos((int)data.pos().x, (int)data.pos().y, (int)data.pos().z);
        C2SCommonSetBlockAirPacketHandler.handle(context.player().level(), pos);
    }
}
