package net.smackplays.smacksutil.networking.s2cpacket;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.smackplays.smacksutil.networking.s2chandlers.S2CCommonBlockBreakPacketHandler;

/**
 * Class S2CBlockBreakPacketHandler */
public class S2CBlockBreakPacketHandler {
    /** Constructor*/
    public S2CBlockBreakPacketHandler() {

    }

    /** Handle BlockBreakPacket
     * @param data data
     * @param context context*/
    public static void handle(S2CBlockBreakPacket data, ClientPlayNetworking.Context context) {
        BlockPos pos = new BlockPos((int)data.pos().x, (int)data.pos().y, (int)data.pos().z);
        S2CCommonBlockBreakPacketHandler.handle(context.player(), pos);
    }
}
