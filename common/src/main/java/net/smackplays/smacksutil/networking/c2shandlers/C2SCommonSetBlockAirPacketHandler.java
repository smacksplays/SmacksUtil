package net.smackplays.smacksutil.networking.c2shandlers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

/**
 * Common Handler for the SetBlockAirPacket */
public class C2SCommonSetBlockAirPacketHandler {
    /** Constructor*/
    C2SCommonSetBlockAirPacketHandler(){

    }
    /** Handle the Packet
     * @param world world
     * @param pos pos*/
    public static void handle(Level world, BlockPos pos) {
        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }
}
