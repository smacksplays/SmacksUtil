package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonVeinMinerBreakPacketHandler;

public class C2SVeinMinerBreakPacketHandler {
    public static void handle(final C2SVeinMinerBreakPacket data, final IPayloadContext context) {
        BlockPos sourcePos = new BlockPos((int)data.sourcePos().x, (int)data.sourcePos().y, (int)data.sourcePos().z);
        BlockPos curr = new BlockPos((int)data.curr().x, (int)data.curr().y, (int)data.curr().z);
        C2SCommonVeinMinerBreakPacketHandler.handle((ServerPlayer) context.player(), context.player().level(), sourcePos, curr, data.isCreative(), data.replaceSeeds());
    }
}
