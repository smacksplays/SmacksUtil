package net.smackplays.smacksutil.networking.s2cpacket;

import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.s2chandlers.S2CCommonBlockBreakPacketHandler;

public class S2CBlockBreakPacketHandler {

    @SuppressWarnings("unused")
    public static void handle(S2CBlockBreakPacket data, IPayloadContext context) {
        BlockPos pos = new BlockPos((int)data.pos().x, (int)data.pos().y, (int)data.pos().z);
        S2CCommonBlockBreakPacketHandler.handle(pos);
    }
}
