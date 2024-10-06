package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonToggleMagnetItemPacketHandler;

public class C2SToggleMagnetItemPacketHandler {
    public static void handle(C2SToggleMagnetItemPacket data, ServerPlayNetworking.Context context) {
        C2SCommonToggleMagnetItemPacketHandler.handle(context.player(), data.slot());
    }
}
