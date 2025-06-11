package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonBackpackSortPacketHandler;

/** class C2SBackpackSortPacketHandler */
public class C2SBackpackSortPacketHandler {

    public static void handle(C2SBackpackSortPacket data, ServerPlayNetworking.Context context) {
        C2SCommonBackpackSortPacketHandler.handle(context.player(), data.slot());
    }
}
