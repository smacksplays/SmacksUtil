package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonBackpackSortPacketHandler;
import org.jetbrains.annotations.NotNull;

public class C2SBackpackSortPacketHandler {

    public static void handle(@NotNull C2SBackpackSortPacket data, IPayloadContext context) {
        C2SCommonBackpackSortPacketHandler.handle((ServerPlayer) context.player(), data.slot());
    }
}
