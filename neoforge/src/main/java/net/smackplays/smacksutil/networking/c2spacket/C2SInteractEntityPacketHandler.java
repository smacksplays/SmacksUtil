package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonInteractEntityPacketHandler;

public class C2SInteractEntityPacketHandler {
    public static void handle(C2SInteractEntityPacket data, IPayloadContext context) {
        C2SCommonInteractEntityPacketHandler.handle((ServerPlayer) context.player(), data.entityUUID(), data.isMainHand());
    }
}
