package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonSetBlockAirPacketHandler;

public class C2SSetBlockAirPacketHandler {
    public static void handle(C2SSetBlockAirPacket data, ServerPlayNetworking.Context context) {
        BlockPos pos = new BlockPos((int)data.pos().x, (int)data.pos().y, (int)data.pos().z);
        C2SCommonSetBlockAirPacketHandler.handle(context.player().level(), pos);
    }
}
