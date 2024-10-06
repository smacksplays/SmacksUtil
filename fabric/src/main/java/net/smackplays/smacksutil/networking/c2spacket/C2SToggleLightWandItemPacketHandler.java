package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonToggleLightWandItemPacketHandler;

public class C2SToggleLightWandItemPacketHandler {
    public static void handle(C2SToggleLightWandItemPacket data, ServerPlayNetworking.Context context) {
        C2SCommonToggleLightWandItemPacketHandler.handle(context.player(), data.slot());
    }
}
