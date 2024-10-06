package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonVeinMinerBreakPacketHandler;

public class C2SVeinMinerBreakPacketHandler {
    public static void handle(final C2SVeinMinerBreakPacket data, final IPayloadContext context) {
        BlockPos pos = new BlockPos((int)data.pos().x, (int)data.pos().y, (int)data.pos().z);
        C2SCommonVeinMinerBreakPacketHandler.handle((ServerPlayer) context.player(), context.player().level(), pos, data.isCreative(), data.replaceSeeds());
    }
}
