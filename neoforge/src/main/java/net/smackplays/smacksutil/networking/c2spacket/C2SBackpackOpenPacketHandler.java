package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonBackpackOpenPacketHandler;

public class C2SBackpackOpenPacketHandler {

    public static void handle(final C2SBackpackOpenPacket data, final IPayloadContext context) {
        C2SCommonBackpackOpenPacketHandler.handle((ServerPlayer) context.player(), data.slot());
    }
}
