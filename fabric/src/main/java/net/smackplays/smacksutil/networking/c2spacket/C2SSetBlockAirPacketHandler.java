package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class C2SSetBlockAirPacketHandler {
    public static void handle(C2SSetBlockAirPacket data, ServerPlayNetworking.Context context) {
        Level world = context.player().level();
        BlockPos pos = new BlockPos((int)data.pos().x, (int)data.pos().y, (int)data.pos().z);
        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }
}
