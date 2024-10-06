package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonToggleMagnetItemPacketHandler;

public class C2SToggleMagnetItemPacketHandler {
    public static void handle(final C2SToggleMagnetItemPacket data, final IPayloadContext context) {
        C2SCommonToggleMagnetItemPacketHandler.handle((ServerPlayer) context.player(), data.slot());
    }
}
