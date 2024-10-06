package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonToggleLightWandItemPacketHandler;

public class C2SToggleLightWandItemPacketHandler {
    public static void handle(final C2SToggleLightWandItemPacket data, final IPayloadContext context) {
        C2SCommonToggleLightWandItemPacketHandler.handle((ServerPlayer) context.player(), data.slot());
    }
}
