package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonSetBlockAirPacketHandler;

/**
 * Class C2SSetBlockAirPacketHandler */
public class C2SSetBlockAirPacketHandler {
    /** Constructor*/
    public C2SSetBlockAirPacketHandler() {

    }
    /** Handle SetBlockAirPacket
     * @param data data
     * @param context context*/
    public static void handle(C2SSetBlockAirPacket data, ServerPlayNetworking.Context context) {
        BlockPos pos = new BlockPos((int)data.pos().x, (int)data.pos().y, (int)data.pos().z);
        C2SCommonSetBlockAirPacketHandler.handle(context.player().level(), pos);
    }
}
