package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonInteractEntityPacketHandler;

public class C2SInteractEntityPacketHandler {
    public static void handle(C2SInteractEntityPacket data, ServerPlayNetworking.Context context) {
        C2SCommonInteractEntityPacketHandler.handle(context.player(), data.entityUUID(), data.hand());
    }
}
