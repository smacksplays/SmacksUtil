package net.smackplays.smacksutil.networking.c2shandlers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class C2SCommonSetBlockAirPacketHandler {
    public static void handle(Level world, BlockPos pos) {
        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }
}
