package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonBackpackOpenPacketHandler;

public class C2SBackpackOpenPacketHandler {

    public static void handle(C2SBackpackOpenPacket data, ServerPlayNetworking.Context context) {
        C2SCommonBackpackOpenPacketHandler.handle(context.player(), data.slot());
    }
}
